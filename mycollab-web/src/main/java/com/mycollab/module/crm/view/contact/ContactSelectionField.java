package com.mycollab.module.crm.view.contact;

import com.mycollab.module.crm.domain.Contact;
import com.mycollab.module.crm.domain.SimpleContact;
import com.mycollab.module.crm.service.ContactService;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.AppUI;
import com.mycollab.vaadin.ui.FieldSelection;
import com.mycollab.vaadin.web.ui.WebThemes;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class ContactSelectionField extends CustomField<Integer> implements FieldSelection<Contact> {
    private static final long serialVersionUID = 1L;

    private TextField contactName;
    private MButton browseBtn, clearBtn;

    private SimpleContact contact;

    public ContactSelectionField() {
        contactName = new TextField();
        contactName.setNullRepresentation("");
        contactName.setWidth("100%");
        browseBtn = new MButton("", clickEvent -> {
            ContactSelectionWindow contactWindow = new ContactSelectionWindow(ContactSelectionField.this);
            UI.getCurrent().addWindow(contactWindow);
            contactWindow.show();
        }).withIcon(FontAwesome.ELLIPSIS_H).withStyleName(WebThemes.BUTTON_OPTION, WebThemes.BUTTON_SMALL_PADDING);

        clearBtn = new MButton("", clickEvent -> {
            contactName.setValue("");
            contact = null;
        }).withIcon(FontAwesome.TRASH_O).withStyleName(WebThemes.BUTTON_OPTION, WebThemes.BUTTON_SMALL_PADDING);
    }

    @Override
    public void fireValueChange(Contact data) {
        contact = (SimpleContact) data;
        if (contact != null) {
            contactName.setValue(contact.getContactName());
        }
    }

    @Override
    public void setPropertyDataSource(Property newDataSource) {
        final Object value = newDataSource.getValue();
        if (value instanceof Integer) {
            setContactByVal((Integer) value);
        }
        super.setPropertyDataSource(newDataSource);
    }

    @Override
    public void commit() throws SourceException, Validator.InvalidValueException {
        if (contact != null) {
            setInternalValue(contact.getId());
        } else {
            setInternalValue(null);
        }
        super.commit();
    }

    private void setContactByVal(Integer contactId) {
        ContactService contactService = AppContextUtil.getSpringBean(ContactService.class);
        SimpleContact contactVal = contactService.findById(contactId, AppUI.getAccountId());
        if (contactVal != null) {
            this.contact = contact;
            contactName.setValue(contact.getContactName());
        }
    }

    public SimpleContact getContact() {
        return this.contact;
    }

    @Override
    protected Component initContent() {
        return new MHorizontalLayout(contactName, browseBtn, clearBtn).expand(contactName)
                .alignAll(Alignment.MIDDLE_LEFT).withFullWidth();
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
