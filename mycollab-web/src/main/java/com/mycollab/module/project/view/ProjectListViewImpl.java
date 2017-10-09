package com.mycollab.module.project.view;

import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Div;
import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.common.i18n.OptionI18nEnum.StatusI18nEnum;
import com.mycollab.core.utils.StringUtils;
import com.mycollab.module.crm.CrmTypeConstants;
import com.mycollab.module.crm.ui.CrmAssetsManager;
import com.mycollab.module.project.ProjectLinkBuilder;
import com.mycollab.module.project.ProjectTypeConstants;
import com.mycollab.module.project.domain.Project;
import com.mycollab.module.project.domain.SimpleProject;
import com.mycollab.module.project.domain.criteria.ProjectSearchCriteria;
import com.mycollab.module.project.fielddef.ProjectTableFieldDef;
import com.mycollab.module.project.service.ProjectService;
import com.mycollab.module.project.ui.ProjectAssetsUtil;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.TooltipHelper;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.event.HasMassItemActionHandler;
import com.mycollab.vaadin.event.HasSearchHandlers;
import com.mycollab.vaadin.event.HasSelectableItemHandlers;
import com.mycollab.vaadin.event.HasSelectionOptionHandlers;
import com.mycollab.vaadin.mvp.AbstractVerticalPageView;
import com.mycollab.vaadin.mvp.ViewComponent;
import com.mycollab.vaadin.ui.DefaultMassItemActionHandlerContainer;
import com.mycollab.vaadin.ui.ELabel;
import com.mycollab.vaadin.ui.UIConstants;
import com.mycollab.vaadin.web.ui.CheckBoxDecor;
import com.mycollab.vaadin.web.ui.LabelLink;
import com.mycollab.vaadin.web.ui.SelectionOptionButton;
import com.mycollab.vaadin.web.ui.WebThemes;
import com.mycollab.vaadin.web.ui.table.DefaultPagedBeanTable;
import com.mycollab.vaadin.web.ui.table.IPagedBeanTable;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import java.util.Arrays;

/**
 * @author MyCollab Ltd
 * @since 5.2.12
 */
@ViewComponent
public class ProjectListViewImpl extends AbstractVerticalPageView implements ProjectListView {
    private ProjectSearchPanel projectSearchPanel;
    private SelectionOptionButton selectOptionButton;
    private DefaultPagedBeanTable<ProjectService, ProjectSearchCriteria, SimpleProject> tableItem;
    private VerticalLayout bodyLayout;
    private DefaultMassItemActionHandlerContainer tableActionControls;
    private Label selectedItemsNumberLabel = new Label();

    public ProjectListViewImpl() {
        withMargin(true);
    }

    @Override
    public void initContent() {
        removeAllComponents();
        projectSearchPanel = new ProjectSearchPanel();
        with(projectSearchPanel);

        bodyLayout = new VerticalLayout();
        this.addComponent(bodyLayout);

        generateDisplayTable();
    }

    private void generateDisplayTable() {
        tableItem = new DefaultPagedBeanTable<>(AppContextUtil.getSpringBean(ProjectService.class),
                SimpleProject.class, ProjectTypeConstants.PROJECT,
                ProjectTableFieldDef.selected, Arrays.asList(ProjectTableFieldDef.projectName,
                ProjectTableFieldDef.lead, ProjectTableFieldDef.client, ProjectTableFieldDef.startDate,
                ProjectTableFieldDef.status));

        tableItem.addGeneratedColumn("selected", (source, itemId, columnId) -> {
            final SimpleProject item = tableItem.getBeanByIndex(itemId);
            final CheckBoxDecor cb = new CheckBoxDecor("", item.isSelected());
            cb.setImmediate(true);
            cb.addValueChangeListener(valueChangeEvent -> tableItem.fireSelectItemEvent(item));
            item.setExtraData(cb);
            return cb;
        });

        tableItem.addGeneratedColumn(Project.Field.name.name(), (source, itemId, columnId) -> {
            SimpleProject project = tableItem.getBeanByIndex(itemId);
            A projectLink = new A(ProjectLinkBuilder.generateProjectFullLink(project.getId())).appendText(project.getName());
            projectLink.setAttribute("onmouseover", TooltipHelper.projectHoverJsFunction(ProjectTypeConstants.PROJECT,
                    project.getId() + ""));
            projectLink.setAttribute("onmouseleave", TooltipHelper.itemMouseLeaveJsFunction());
            A url;
            if (StringUtils.isNotBlank(project.getHomepage())) {
                url = new A(project.getHomepage(), "_blank").appendText(project.getHomepage()).setCSSClass(UIConstants.META_INFO);
            } else {
                url = new A("").appendText(UserUIContext.getMessage(GenericI18Enum.OPT_UNDEFINED));
            }

            Div projectDiv = new Div().appendChild(projectLink, new Br(), url);
            ELabel b = ELabel.html(projectDiv.write());
            return new MHorizontalLayout(ProjectAssetsUtil.projectLogoComp(project
                    .getShortname(), project.getId(), project.getAvatarid(), 32), b)
                    .expand(b).alignAll(Alignment.MIDDLE_LEFT).withMargin(false);
        });

        tableItem.addGeneratedColumn(Project.Field.lead.name(), (source, itemId, columnId) -> {
            SimpleProject project = tableItem.getBeanByIndex(itemId);
            return new Label(ProjectLinkBuilder.generateProjectMemberHtmlLink(project.getId(), project.getLead(),
                    project.getLeadFullName(), project.getLeadAvatarId(), true), ContentMode.HTML);
        });

        tableItem.addGeneratedColumn(Project.Field.accountid.name(), (source, itemId, columnId) -> {
            SimpleProject project = tableItem.getBeanByIndex(itemId);
            if (project.getAccountid() != null) {
                LabelLink b = new LabelLink(project.getClientName(), ProjectLinkBuilder.generateClientPreviewFullLink
                        (project.getAccountid()));
                b.setIconLink(CrmAssetsManager.getAsset(CrmTypeConstants.ACCOUNT));
                return b;
            } else {
                return new Label();
            }
        });

        tableItem.addGeneratedColumn(Project.Field.planstartdate.name(), (source, itemId, columnId) -> {
            SimpleProject project = tableItem.getBeanByIndex(itemId);
            return new Label(UserUIContext.formatDate(project.getPlanstartdate()));
        });

        tableItem.addGeneratedColumn(Project.Field.planenddate.name(), (source, itemId, columnId) -> {
            SimpleProject project = tableItem.getBeanByIndex(itemId);
            return new Label(UserUIContext.formatDate(project.getPlanenddate()));
        });

        tableItem.addGeneratedColumn(Project.Field.projectstatus.name(), (source, itemId, columnId) -> {
            SimpleProject project = tableItem.getBeanByIndex(itemId);
            return ELabel.i18n(project.getProjectstatus(), StatusI18nEnum.class);
        });

        tableItem.addGeneratedColumn(Project.Field.createdtime.name(), (source, itemId, columnId) -> {
            SimpleProject project = tableItem.getBeanByIndex(itemId);
            return new Label(UserUIContext.formatDate(project.getCreatedtime()));
        });

        tableItem.setWidth("100%");

        bodyLayout.addComponent(constructTableActionControls());
        bodyLayout.addComponent(tableItem);
    }

    private ComponentContainer constructTableActionControls() {
        MHorizontalLayout layout = new MHorizontalLayout().withFullWidth();
        layout.addStyleName(WebThemes.TABLE_ACTION_CONTROLS);

        selectOptionButton = new SelectionOptionButton(tableItem);
        selectOptionButton.setWidthUndefined();
        layout.addComponent(selectOptionButton);

        tableActionControls = new DefaultMassItemActionHandlerContainer();

        tableActionControls.addDownloadPdfActionItem();
        tableActionControls.addDownloadExcelActionItem();
        tableActionControls.addDownloadCsvActionItem();

        tableActionControls.setVisible(false);
        tableActionControls.setWidthUndefined();

        layout.addComponent(tableActionControls);
        selectedItemsNumberLabel.setWidth("100%");
        layout.with(selectedItemsNumberLabel).withAlign(selectedItemsNumberLabel, Alignment.MIDDLE_CENTER).expand(selectedItemsNumberLabel);

        MButton customizeViewBtn = new MButton("", clickEvent -> UI.getCurrent().addWindow(new ProjectListCustomizeWindow(tableItem)))
                .withStyleName(WebThemes.BUTTON_ACTION).withIcon(FontAwesome.ADJUST);
        customizeViewBtn.setDescription(UserUIContext.getMessage(GenericI18Enum.OPT_LAYOUT_OPTIONS));
        layout.with(customizeViewBtn).withAlign(customizeViewBtn, Alignment.MIDDLE_RIGHT);

        return layout;
    }

    @Override
    public void showNoItemView() {

    }

    @Override
    public void enableActionControls(int numOfSelectedItems) {
        tableActionControls.setVisible(true);
        selectedItemsNumberLabel.setValue(UserUIContext.getMessage(GenericI18Enum.TABLE_SELECTED_ITEM_TITLE, numOfSelectedItems));
    }

    @Override
    public void disableActionControls() {
        tableActionControls.setVisible(false);
        selectOptionButton.setSelectedCheckbox(false);
        selectedItemsNumberLabel.setValue("");
    }

    @Override
    public HasSearchHandlers<ProjectSearchCriteria> getSearchHandlers() {
        return projectSearchPanel;
    }

    @Override
    public HasSelectionOptionHandlers getOptionSelectionHandlers() {
        return selectOptionButton;
    }

    @Override
    public HasMassItemActionHandler getPopupActionHandlers() {
        return tableActionControls;
    }

    @Override
    public HasSelectableItemHandlers<SimpleProject> getSelectableItemHandlers() {
        return tableItem;
    }

    @Override
    public IPagedBeanTable<ProjectSearchCriteria, SimpleProject> getPagedBeanTable() {
        return tableItem;
    }
}
