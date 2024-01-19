package org.vaadin.referencearchitecture.helloworld.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.referencearchitecture.helloworld.service.Greeter;
import org.vaadin.referencearchitecture.helloworld.ui.layouts.MainLayout;

@Route(value = "", layout = MainLayout.class)
public class HelloView extends VerticalLayout {

    private final Greeter greeter;

    public HelloView(Greeter greeter) {
        this.greeter = greeter;
        var name = new TextField("Please enter your name");
        var sayHello = new Button("Say Hello", event -> sayHello(name.getValue()));
        add(name, sayHello);
    }

    private void sayHello(String name) {
        Notification.show(greeter.sayHello(name));
    }
}
