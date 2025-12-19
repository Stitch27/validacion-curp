package com.fincomun.validadorcurp.tools.definition;

import javax.validation.constraints.NotNull;

public interface IPrintingTool {
    <T> String prinObjectAsJson(@NotNull T t);

    String randomString ();

    String getPID();
}
