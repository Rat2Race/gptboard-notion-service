package org.rater.reviewapp.notion.util;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.function.Function;
import org.rater.reviewapp.notion.dto.NotionTextBlockDto;
import java.util.*;

public class NotionTextBlockParser {

    public static List<String> extractPlainTextsFromRichTextArray(JsonNode blockRichTextList) {
        List<String> plainTextList = new ArrayList<>();
        if (blockRichTextList != null && blockRichTextList.isArray()) {
            for (JsonNode richTextItem : blockRichTextList) {
                plainTextList.add(richTextItem.get("plain_text").asText());
            }
        }
        return plainTextList;
    }

    public static List<String> extractPlainTextsFromNotionBlock(NotionTextBlockDto notionBlock) {
        List<String> plainTextList = new ArrayList<>();
        JsonNode blockData = notionBlock.data();
        if (blockData == null) {
            return plainTextList;
        }
        switch (notionBlock.type()) {
            case "paragraph" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("paragraph").get("rich_text")));
            case "heading_1" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("heading_1").get("rich_text")));
            case "heading_2" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("heading_2").get("rich_text")));
            case "heading_3" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("heading_3").get("rich_text")));
            case "bulleted_list_item" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("bulleted_list_item").get("rich_text")));
            case "numbered_list_item" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("numbered_list_item").get("rich_text")));
            case "to_do" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("to_do").get("rich_text")));
            case "toggle" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("toggle").get("rich_text")));
            case "quote" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("quote").get("rich_text")));
            case "callout" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("callout").get("rich_text")));
            case "code" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("code").get("rich_text")));
            case "table" -> plainTextList.addAll(extractPlainTextsFromRichTextArray(blockData.get("table").get("rich_text")));
        }
        return plainTextList;
    }

    /**
     * 재귀 호출하면서 모든 depth 블록들을 호출해서 파싱함
     * @param notionBlock ( extractPlainTextsFromNotionBlock(notionBlock) )
     * @param fetchChildrenFunc ( notionBlock.id -> List<NotionTextBlockDto> )
     * @return
     */
    public static List<String> extractAllTextsRecursive(NotionTextBlockDto notionBlock, Function<String, List<NotionTextBlockDto>> fetchChildrenFunc) {
        List<String> plainTextList = extractPlainTextsFromNotionBlock(notionBlock);

        if (notionBlock.hasChildren()) {
            List<NotionTextBlockDto> children = fetchChildrenFunc.apply(notionBlock.id());

            for (NotionTextBlockDto child : children) {
                plainTextList.addAll(extractAllTextsRecursive(child, fetchChildrenFunc));
            }
        }

        return plainTextList;
    }

}
