package com.mycollab.module.crm.view.contact;

import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.vaadin.EventBusFactory;
import com.mycollab.module.crm.CrmTypeConstants;
import com.mycollab.module.crm.event.ContactEvent;
import com.mycollab.module.crm.i18n.ContactI18nEnum;
import com.mycollab.module.crm.ui.CrmAssetsManager;
import com.mycollab.module.crm.ui.components.CrmListNoItemView;
import com.mycollab.security.RolePermissionCollections;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.mvp.ViewComponent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author MyCollab Ltd.
 * @since 4.0
 */
@ViewComponent
public class ContactCrmListNoItemView extends CrmListNoItemView {
    private static final long serialVersionUID = 1L;

    @Override
    protected FontAwesome titleIcon() {
        return CrmAssetsManager.getAsset(CrmTypeConstants.CONTACT);
    }

    @Override
    protected String titleMessage() {
        return UserUIContext.getMessage(GenericI18Enum.VIEW_NO_ITEM_TITLE);
    }

    @Override
    protected String hintMessage() {
        return UserUIContext.getMessage(GenericI18Enum.VIEW_NO_ITEM_HINT);
    }

    @Override
    protected String actionMessage() {
        return UserUIContext.getMessage(ContactI18nEnum.NEW);
    }

    @Override
    protected Button.ClickListener actionListener() {
        return new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                EventBusFactory.getInstance().post(new ContactEvent.GotoAdd(this, null));
            }
        };
    }

    @Override
    protected boolean hasPermission() {
        return UserUIContext.canWrite(RolePermissionCollections.CRM_CONTACT);
    }
}
