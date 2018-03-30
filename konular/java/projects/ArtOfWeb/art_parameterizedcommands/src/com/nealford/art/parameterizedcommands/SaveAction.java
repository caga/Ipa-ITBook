package com.nealford.art.parameterizedcommands;

public class SaveAction extends Action {

    public void execute() {
        TheModel model = (TheModel) getRequest().getSession().
                getAttribute("model");
        model.addKeyword(getRequest().getParameter("keyword"));
        forward("controller?cmd=listing");
    }
}