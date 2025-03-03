package us.hebi.samples.gui;

/**
 * @author Florian Enner
 * @since 09 Nov 2023
 */
public class MinimalFxAppLauncher {

    public static void main(String[] args) throws Exception {
        // We can't set properties on a shared lib, so we need to manually add fx properties
        System.setProperty("javafx.verbose", "true");
        System.setProperty("javafx.debug", "true");
        System.setProperty("prism.verbose", "true");
        System.setProperty("prism.debug", "true");
        System.setProperty("quantum.verbose", "true");
        System.setProperty("quantum.debug", "true");
        System.setProperty("quantum.pulsedebug", "true");
        System.out.println(Class.forName("javafx.application.Application"));
        System.out.println(Class.forName("us.hebi.samples.gui.MinimalFxApp"));
        MinimalFxApp.main(args);
    }

}
