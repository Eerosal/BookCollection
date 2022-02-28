package fi.eerosalla.web.bookcollectionapp.model.form;

import fi.eerosalla.web.bookcollectionapp.validation.AllValuesOptionalGroup;
import fi.eerosalla.web.bookcollectionapp.validation.AllValuesRequiredGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookForm {

    @NotEmpty(groups = AllValuesRequiredGroup.class)
    @Size(min = 1, groups = AllValuesOptionalGroup.class)
    @Size(max = 255, groups = {
        AllValuesRequiredGroup.class,
        AllValuesOptionalGroup.class
    })
    private String title;

    @NotEmpty(groups = AllValuesRequiredGroup.class)
    @Size(min = 1, groups = AllValuesOptionalGroup.class)
    @Size(max = 255, groups = {
        AllValuesRequiredGroup.class,
        AllValuesOptionalGroup.class
    })
    private String author;

    @NotNull(groups = AllValuesRequiredGroup.class)
    @Size(max = 65535, groups = {
        AllValuesRequiredGroup.class,
        AllValuesOptionalGroup.class
    })
    private String description;

}
