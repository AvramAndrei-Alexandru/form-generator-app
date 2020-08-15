package com.model;

import com.form_classes.FormQuestions;
import com.form_classes.FreeTextQuestions;
import com.form_classes.RadioButtonQuestions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultQuestionGenerator {

    private UniqueIDGenerator uniqueIDGenerator = UniqueIDGenerator.getUniqueIDGenerator();


    public  List<FormQuestions> generateDefaultQuestions() {
        List<FormQuestions> questions = new ArrayList<>();
        Map<String, Integer> answerMap = new LinkedHashMap<>();

        answerMap.put("masculin", 1);
        answerMap.put("feminin", 2);
        questions.add(generateRadioButtonQuestion(answerMap.keySet().size(),"Sex:", answerMap));

        questions.add(generateFreeTextQuestions("Varsta implinita:"));

        answerMap = new LinkedHashMap<>();
        answerMap.put("primare (fara scoala, 4-8 clase, scoala de ucenici)", 1);
        answerMap.put("medii (liceu, scoala profesionala, postliceala)", 2);
        answerMap.put("superioare (facultate)", 3);
        questions.add(generateRadioButtonQuestion(answerMap.keySet().size(), "Studii (ultima scoala absolvita):", answerMap));

        answerMap = new LinkedHashMap<>();
        answerMap.put("salariat la stat", 1);
        answerMap.put("salariat la privat", 2);
        answerMap.put("liber profesionist, mester", 3);
        answerMap.put("intreprinzator, patron", 4);
        answerMap.put("pensionar", 5);
        answerMap.put("elev, student", 6);
        answerMap.put("somer, fara ocupatie, casnica", 7);
        answerMap.put("alta ocupatie", 8);
        questions.add(generateRadioButtonQuestion(answerMap.keySet().size(), "Ocupatia:", answerMap));

        answerMap = new LinkedHashMap<>();
        answerMap.put("fara venit", 1);
        answerMap.put("sub 1.200 lei", 2);
        answerMap.put("1.201-2.000 lei", 3);
        answerMap.put("2.001-3.000 lei", 4);
        answerMap.put("3.001-5.000 lei", 5);
        answerMap.put("peste 5.001 lei", 6);
        answerMap.put("NR", 98);
        questions.add(generateRadioButtonQuestion(answerMap.keySet().size(), "Venitul dvs. din luna trecuta:", answerMap));

        answerMap = new LinkedHashMap<>();
        answerMap.put("Urban", 1);
        answerMap.put("Rural", 2);
        questions.add(generateRadioButtonQuestion(answerMap.keySet().size(), "Mediul de rezidenta:", answerMap));

        questions.add(generateRadioButtonQuestion(2, "Aveti rude in strainatate cu care tineti legatura in mod regulat?", generateYesNoMap()));

        questions.add(generateRadioButtonQuestion(2, "Dvs. aveti acces la internet?", generateYesNoMap()));

        questions.add(generateRadioButtonQuestion(2, "Dvs. aveti un cont de Facebook sau Twitter?", generateYesNoMap()));

        answerMap = new LinkedHashMap<>();
        answerMap.put("Niciuna", 1);
        answerMap.put("Una", 2);
        answerMap.put("Doua sau mai multe", 3);
        answerMap.put("NR", 98);
        questions.add(generateRadioButtonQuestion(answerMap.keySet().size(), "Cate limbi straine cunoasteti?", answerMap));

        questions.add(generateFreeTextQuestions("Localitate:"));
        questions.add(generateFreeTextQuestions("Judet:"));
        questions.add(generateFreeTextQuestions("Data:"));
        questions.add(generateFreeTextQuestions("Operator:"));
        questions.add(generateFreeTextQuestions("Numar de telefon:"));
        return questions;
    }

    private RadioButtonQuestions generateRadioButtonQuestion(int numberOfAnswers, String questionText, Map<String, Integer> answerMap) {
        RadioButtonQuestions radioButtonQuestions = new RadioButtonQuestions(uniqueIDGenerator.generateUniqueID(), numberOfAnswers, questionText);
        List<String> answerList = new ArrayList<>(answerMap.keySet());
        radioButtonQuestions.setAnswerList(answerList);
        return radioButtonQuestions;
    }

    private FreeTextQuestions generateFreeTextQuestions(String questionText) {
        FreeTextQuestions freeTextQuestions = new FreeTextQuestions(uniqueIDGenerator.generateUniqueID(), questionText);
        return freeTextQuestions;
    }

    private Map<String, Integer> generateYesNoMap() {
        Map<String, Integer> answerMap = new LinkedHashMap<>();
        answerMap.put("Da", 1);
        answerMap.put("Nu", 2);
        return answerMap;
    }
}
