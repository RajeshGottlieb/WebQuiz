package com.webquiz.service;

import com.webquiz.dao.*;
import com.webquiz.model.QuizSelection;

public class QuizSelectorService {

    private QuizSelectionDao qsdao = null;

    public QuizSelectorService() {
        qsdao = new QuizSelectionDao();
        return;
    }

    public QuizSelection populate() {
        return qsdao.populate();
    }

}
