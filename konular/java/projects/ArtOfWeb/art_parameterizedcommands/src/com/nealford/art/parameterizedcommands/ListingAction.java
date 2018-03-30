package com.nealford.art.parameterizedcommands;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;

public class ListingAction extends Action {

    public void execute() {
        TheModel model = getOrCreateModel();
        List sortedKeywords = getSortedKeywords(model);
        bundleAttributesForView(model, sortedKeywords);
        forwardToView();
    }

    private TheModel getOrCreateModel() {
        HttpSession session = getRequest().getSession(true);
        TheModel model = null;
        model = (TheModel) session.getAttribute("model");
        if (model == null) {
            model = new TheModel();
            session.setAttribute("model", model);
        }
        return model;
    }

    private List getSortedKeywords(TheModel model) {
        List sortedKeywords = model.getKeywords();
        Collections.sort(sortedKeywords);
        return sortedKeywords;
    }

    private void bundleAttributesForView(TheModel model,
            List sortedKeywords) {
        getRequest().setAttribute("keywords", sortedKeywords);
        getRequest().setAttribute("proposed",
                model.getProposedKeywords());
    }

    private void forwardToView() {
        forward("/Listing.jsp");
    }
}