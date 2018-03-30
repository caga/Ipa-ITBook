package com.nealford.art.history.customtags.stl;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

public class AddDbPoolTagExtraInfo extends TagExtraInfo {
    public AddDbPoolTagExtraInfo() {}

    public boolean isValid(TagData d) {
        return checkData(d.getAttribute("initDriverClassName")) &&
               checkData(d.getAttribute("initUrlName")) &&
               checkData(d.getAttribute("initUserName")) &&
               checkData(d.getAttribute("initPasswordName"));
    }

    private boolean checkData(Object toBeChecked) {
        return (toBeChecked != null) &&
               (((String) toBeChecked).length() > 0);
    }
}
