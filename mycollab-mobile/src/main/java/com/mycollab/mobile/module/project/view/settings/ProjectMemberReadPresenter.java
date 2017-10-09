package com.mycollab.mobile.module.project.view.settings;

import com.mycollab.mobile.module.project.view.AbstractProjectPresenter;
import com.mycollab.module.project.CurrentProjectVariables;
import com.mycollab.module.project.ProjectRolePermissionCollections;
import com.mycollab.module.project.domain.SimpleProjectMember;
import com.mycollab.module.project.service.ProjectMemberService;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.AppUI;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.mvp.ScreenData;
import com.mycollab.vaadin.ui.NotificationUtil;
import com.vaadin.ui.HasComponents;

/**
 * @author MyCollab Ltd.
 * @since 4.5.2
 */
public class ProjectMemberReadPresenter extends AbstractProjectPresenter<ProjectMemberReadView> {
    private static final long serialVersionUID = 1287812620895443711L;

    public ProjectMemberReadPresenter() {
        super(ProjectMemberReadView.class);
    }

    @Override
    protected void onGo(HasComponents container, ScreenData<?> data) {
        boolean isCurrentUserAccess = false;

        if (data.getParams() instanceof String) {
            if (UserUIContext.getUsername().equals(data.getParams())) {
                isCurrentUserAccess = true;
            }
        }
        if (CurrentProjectVariables.canRead(ProjectRolePermissionCollections.USERS) || isCurrentUserAccess) {
            ProjectMemberService prjMemberService = AppContextUtil.getSpringBean(ProjectMemberService.class);
            SimpleProjectMember prjMember = null;
            if (data.getParams() instanceof Integer) {
                prjMember = prjMemberService.findById((Integer) data.getParams(), AppUI.getAccountId());

            } else if (data.getParams() instanceof String) {
                String username = (String) data.getParams();
                prjMember = prjMemberService.findMemberByUsername(username, CurrentProjectVariables.getProjectId(), AppUI.getAccountId());
            }
            if (prjMember != null) {
                this.getView().previewItem(prjMember);
                super.onGo(container, data);
            } else {
                NotificationUtil.showRecordNotExistNotification();
            }
        } else {
            NotificationUtil.showMessagePermissionAlert();
        }
    }

}
