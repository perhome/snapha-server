//package cn.perhome.snapha.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apdplat.word.WordSegmenter;
//import org.apdplat.word.dictionary.DictionaryFactory;
//import org.apdplat.word.segmentation.Segmentation;
//import org.apdplat.word.segmentation.SegmentationAlgorithm;
//import org.apdplat.word.segmentation.SegmentationFactory;
//import org.apdplat.word.util.WordConfTools;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.apdplat.word.segmentation.Word;
//
//@Slf4j
//public class SegUtils {
//    private static Segmentation segmentation = null;
//
//    public static void init() {
//        segmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching);
//        List<Word> test = segmentation.seg("骁禾软件农业信息系统");
//        log.info("分词测试结果 {}", test);
//    }
//
//    public  static  List<String> process(String text) {
//        List<Word> words = segmentation.seg(text);
//        Set<String> resultSet = new HashSet<>();
//        for (Word word : words){
//            resultSet.add(word.getText());
//        }
//        return new ArrayList<>(resultSet);
//    }
//}
