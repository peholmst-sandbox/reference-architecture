package org.vaadin.referencearchitecture.helloworld.ui.layouts;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.referencearchitecture.helloworld.ui.views.HelloView;


public class MainLayout extends AppLayout {

    public MainLayout() {
        var toggle = new DrawerToggle();

        var title = new H1("Hello World");
        title.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.NONE
        );

        var nav = createSideNav();
        var scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToNavbar(toggle, title);
        addToDrawer(scroller);
    }

    private SideNav createSideNav() {
        var sideNav = new SideNav();
        sideNav.addItem(new SideNavItem("Hello View", HelloView.class, VaadinIcon.HOME.create()));
        return sideNav;
    }
}
