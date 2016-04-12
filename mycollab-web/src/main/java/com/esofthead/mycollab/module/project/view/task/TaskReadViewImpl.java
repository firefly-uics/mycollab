/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.project.view.task;

import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.common.i18n.OptionI18nEnum.StatusI18nEnum;
import com.esofthead.mycollab.core.arguments.ValuedBean;
import com.esofthead.mycollab.core.utils.BeanUtility;
import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.ProjectLinkBuilder;
import com.esofthead.mycollab.module.project.ProjectRolePermissionCollections;
import com.esofthead.mycollab.module.project.ProjectTypeConstants;
import com.esofthead.mycollab.module.project.domain.SimpleTask;
import com.esofthead.mycollab.module.project.i18n.ProjectCommonI18nEnum;
import com.esofthead.mycollab.module.project.i18n.TaskI18nEnum;
import com.esofthead.mycollab.module.project.service.ProjectTaskService;
import com.esofthead.mycollab.module.project.ui.ProjectAssetsManager;
import com.esofthead.mycollab.module.project.ui.components.*;
import com.esofthead.mycollab.module.project.view.task.components.TaskTimeLogSheet;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.utils.TooltipHelper;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.events.HasPreviewFormHandlers;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.mvp.ViewManager;
import com.esofthead.mycollab.vaadin.ui.ELabel;
import com.esofthead.mycollab.vaadin.web.ui.AdvancedPreviewBeanForm;
import com.esofthead.mycollab.vaadin.web.ui.ProjectPreviewFormControlsGenerator;
import com.esofthead.mycollab.vaadin.web.ui.ReadViewLayout;
import com.esofthead.mycollab.vaadin.web.ui.UIConstants;
import com.hp.gagawa.java.elements.A;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import static com.esofthead.mycollab.utils.TooltipHelper.TOOLTIP_ID;

/**
 * @author MyCollab Ltd.
 * @since 2.0
 */
@ViewComponent
public class TaskReadViewImpl extends AbstractPreviewItemComp<SimpleTask> implements TaskReadView {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(TaskReadViewImpl.class);

    private ProjectActivityComponent activityComponent;
    private TagViewComponent tagViewComponent;
    private ProjectFollowersComp<SimpleTask> followerSheet;
    private DateInfoComp dateInfoComp;
    private TaskTimeLogSheet timesheetComp;
    private PeopleInfoComp peopleInfoComp;
    private Button quickActionStatusBtn;

    public TaskReadViewImpl() {
        super(AppContext.getMessage(TaskI18nEnum.VIEW_DETAIL_TITLE),
                ProjectAssetsManager.getAsset(ProjectTypeConstants.TASK), new TaskPreviewFormLayout());
    }

    @Override
    public SimpleTask getItem() {
        return beanItem;
    }

    @Override
    public ComponentContainer getWidget() {
        return this;
    }

    @Override
    public HasPreviewFormHandlers<SimpleTask> getPreviewFormHandlers() {
        return previewForm;
    }

    @Override
    protected void initRelatedComponents() {
        activityComponent = new ProjectActivityComponent(ProjectTypeConstants.TASK, CurrentProjectVariables.getProjectId());
        dateInfoComp = new DateInfoComp();
        peopleInfoComp = new PeopleInfoComp();
        followerSheet = new ProjectFollowersComp<>(ProjectTypeConstants.TASK, ProjectRolePermissionCollections.TASKS);
        timesheetComp = ViewManager.getCacheComponent(TaskTimeLogSheet.class);

        addToSideBar(dateInfoComp, peopleInfoComp, timesheetComp, followerSheet);
    }

    @Override
    protected void onPreviewItem() {
        ((TaskPreviewFormLayout) previewLayout).displayTaskHeader(beanItem);
        if (beanItem.isCompleted()) {
            addLayoutStyleName(UIConstants.LINK_COMPLETED);
        } else if (beanItem.isPending()) {
            addLayoutStyleName(UIConstants.LINK_PENDING);
        } else if (beanItem.isOverdue()) {
            addLayoutStyleName(UIConstants.LABEL_OVERDUE);
        }

        if (!beanItem.isCompleted()) {
            quickActionStatusBtn.setCaption(AppContext.getMessage(GenericI18Enum.BUTTON_CLOSE));
            quickActionStatusBtn.setIcon(FontAwesome.ARCHIVE);
        } else {
            quickActionStatusBtn.setCaption(AppContext.getMessage(GenericI18Enum.BUTTON_REOPEN));
            quickActionStatusBtn.setIcon(FontAwesome.CIRCLE_O_NOTCH);
        }

        tagViewComponent.display(ProjectTypeConstants.TASK, beanItem.getId());
        activityComponent.loadActivities("" + beanItem.getId());
        followerSheet.displayFollowers(beanItem);
        peopleInfoComp.displayEntryPeople(beanItem);
        dateInfoComp.displayEntryDateTime(beanItem);
        timesheetComp.displayTime(beanItem);
    }

    @Override
    protected String initFormTitle() {
        return beanItem.getTaskname();
    }

    @Override
    protected AdvancedPreviewBeanForm<SimpleTask> initPreviewForm() {
        return new TaskPreviewForm();
    }

    @Override
    protected ComponentContainer createButtonControls() {
        ProjectPreviewFormControlsGenerator<SimpleTask> taskPreviewForm = new ProjectPreviewFormControlsGenerator<>(previewForm);
        final HorizontalLayout topPanel = taskPreviewForm.createButtonControls(
                ProjectPreviewFormControlsGenerator.ADD_BTN_PRESENTED
                        | ProjectPreviewFormControlsGenerator.ASSIGN_BTN_PRESENTED
                        | ProjectPreviewFormControlsGenerator.CLONE_BTN_PRESENTED
                        | ProjectPreviewFormControlsGenerator.DELETE_BTN_PRESENTED
                        | ProjectPreviewFormControlsGenerator.EDIT_BTN_PRESENTED
                        | ProjectPreviewFormControlsGenerator.PRINT_BTN_PRESENTED,
                ProjectRolePermissionCollections.TASKS);

        quickActionStatusBtn = new Button("", new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                if (beanItem.isCompleted()) {
                    beanItem.setStatus(StatusI18nEnum.Open.name());
                    beanItem.setPercentagecomplete(0d);
                    TaskReadViewImpl.this.removeLayoutStyleName(UIConstants.LINK_COMPLETED);
                    quickActionStatusBtn.setCaption(AppContext.getMessage(GenericI18Enum.BUTTON_CLOSE));
                    quickActionStatusBtn.setIcon(FontAwesome.ARCHIVE);
                } else {
                    beanItem.setStatus(StatusI18nEnum.Closed.name());
                    beanItem.setPercentagecomplete(100d);
                    TaskReadViewImpl.this.addLayoutStyleName(UIConstants.LINK_COMPLETED);
                    quickActionStatusBtn.setCaption(AppContext.getMessage(GenericI18Enum.BUTTON_REOPEN));
                    quickActionStatusBtn.setIcon(FontAwesome.CIRCLE_O_NOTCH);
                }

                ProjectTaskService service = ApplicationContextUtil.getSpringBean(ProjectTaskService.class);
                service.updateWithSession(beanItem, AppContext.getUsername());

            }
        });

        quickActionStatusBtn.setStyleName(UIConstants.BUTTON_ACTION);
        taskPreviewForm.insertToControlBlock(quickActionStatusBtn);

        if (!CurrentProjectVariables.canWrite(ProjectRolePermissionCollections.TASKS)) {
            quickActionStatusBtn.setEnabled(false);
        }

        return topPanel;
    }

    @Override
    protected ComponentContainer createExtraControls() {
        tagViewComponent = new TagViewComponent();
        return tagViewComponent;
    }

    @Override
    protected ComponentContainer createBottomPanel() {
        return activityComponent;
    }

    @Override
    protected String getType() {
        return ProjectTypeConstants.TASK;
    }

    private static class TaskPreviewFormLayout extends ReadViewLayout {
        private ELabel titleLbl;

        void displayTaskHeader(SimpleTask task) {
            if (task.getParenttaskid() == null) {
                MHorizontalLayout header = new MHorizontalLayout().withWidth("100%");
                titleLbl = ELabel.h3(task.getTaskname());
                header.with(titleLbl).expand(titleLbl);
                this.addHeader(header);
            } else {
                MVerticalLayout header = new MVerticalLayout().withMargin(false);
                Label parentLabel = new Label(buildParentTaskLink(task), ContentMode.HTML);

                titleLbl = ELabel.h3("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + task.getTaskname());
                header.with(parentLabel, titleLbl);
                this.addHeader(header);
            }

            if (task.isCompleted()) {
                titleLbl.removeStyleName("pending overdue");
                titleLbl.addStyleName("completed");
            } else if (task.isPending()) {
                titleLbl.removeStyleName("completed overdue");
                titleLbl.addStyleName("pending");
            } else if (task.isOverdue()) {
                titleLbl.removeStyleName("completed pending");
                titleLbl.addStyleName("overdue");
            }
        }

        private String buildParentTaskLink(SimpleTask task) {
            String linkName = String.format("[#%d] - %s", task.getParentTaskKey(), task.getParentTaskName());
            A taskLink = new A().setId("tag" + TOOLTIP_ID).setHref(ProjectLinkBuilder.generateTaskPreviewFullLink(task.getParentTaskKey(),
                    CurrentProjectVariables.getShortName())).appendText(linkName).setStyle("display:inline");
            taskLink.setAttribute("onmouseover", TooltipHelper.projectHoverJsFunction(ProjectTypeConstants.TASK, task.getId() + ""));
            taskLink.setAttribute("onmouseleave", TooltipHelper.itemMouseLeaveJsFunction());
            return taskLink.write();
        }

        @Override
        public void addTitleStyleName(String styleName) {
            titleLbl.addStyleName(styleName);
        }

        @Override
        public void removeTitleStyleName(String styleName) {
            titleLbl.removeStyleName(styleName);
        }

        @Override
        public void setTitle(String title) {
        }
    }

    private static class PeopleInfoComp extends MVerticalLayout {
        private static final long serialVersionUID = 1L;

        void displayEntryPeople(ValuedBean bean) {
            this.removeAllComponents();
            this.withMargin(false);

            Label peopleInfoHeader = new Label(FontAwesome.USER.getHtml() + " " +
                    AppContext.getMessage(ProjectCommonI18nEnum.SUB_INFO_PEOPLE), ContentMode.HTML);
            peopleInfoHeader.setStyleName("info-hdr");
            this.addComponent(peopleInfoHeader);

            GridLayout layout = new GridLayout(2, 2);
            layout.setWidth("100%");
            layout.setMargin(new MarginInfo(false, false, false, true));
            try {
                Label createdLbl = new Label(AppContext.getMessage(ProjectCommonI18nEnum.ITEM_CREATED_PEOPLE));
                createdLbl.setSizeUndefined();
                layout.addComponent(createdLbl, 0, 0);

                String createdUserName = (String) PropertyUtils.getProperty(bean, "logby");
                String createdUserAvatarId = (String) PropertyUtils.getProperty(bean, "logByAvatarId");
                String createdUserDisplayName = (String) PropertyUtils.getProperty(bean, "logByFullName");

                ProjectMemberLink createdUserLink = new ProjectMemberLink(createdUserName,
                        createdUserAvatarId, createdUserDisplayName);
                layout.addComponent(createdUserLink, 1, 0);
                layout.setColumnExpandRatio(1, 1.0f);

                Label assigneeLbl = new Label(AppContext.getMessage(ProjectCommonI18nEnum.ITEM_ASSIGN_PEOPLE));
                assigneeLbl.setSizeUndefined();
                layout.addComponent(assigneeLbl, 0, 1);
                String assignUserName = (String) PropertyUtils.getProperty(bean, "assignuser");
                String assignUserAvatarId = (String) PropertyUtils.getProperty(bean, "assignUserAvatarId");
                String assignUserDisplayName = (String) PropertyUtils.getProperty(bean, "assignUserFullName");

                ProjectMemberLink assignUserLink = new ProjectMemberLink(assignUserName, assignUserAvatarId, assignUserDisplayName);
                layout.addComponent(assignUserLink, 1, 1);
            } catch (Exception e) {
                LOG.error("Can not build user link {} ", BeanUtility.printBeanObj(bean));
            }

            this.addComponent(layout);
        }
    }
}