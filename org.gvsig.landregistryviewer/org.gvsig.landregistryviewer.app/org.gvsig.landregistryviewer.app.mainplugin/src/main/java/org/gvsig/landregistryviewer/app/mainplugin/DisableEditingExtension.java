/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gvsig.landregistryviewer.app.mainplugin;

import org.gvsig.andami.PluginsLocator;
import org.gvsig.andami.PluginsManager;
import org.gvsig.andami.actioninfo.ActionInfo;
import org.gvsig.andami.actioninfo.ActionInfoManager;
import org.gvsig.andami.plugins.Extension;

/**
 *
 * @author jjdelcerro
 */
public class DisableEditingExtension extends Extension {

    private LandRegistryViewerExtension landRegistryViewerExtension = null;
    private ActionInfo startEditingAction;

    public void initialize() {

    }

    public void postInitialize() {
        PluginsManager pluginManager = PluginsLocator.getManager();
        this.landRegistryViewerExtension = (LandRegistryViewerExtension) pluginManager
                .getExtension(LandRegistryViewerExtension.class);

        ActionInfoManager actionManager = PluginsLocator.getActionInfoManager();
        this.startEditingAction = actionManager.getAction("layer-start-editing");
        if (this.startEditingAction != null) {
            try {
                this.startEditingAction = (ActionInfo) this.startEditingAction.clone();
            } catch (CloneNotSupportedException ex) {
                // Por aqui no deberia pasar ya que ActionInfo siempre implementa este metodo.
            }
            actionManager.redirect("layer-start-editing", "layer-start-editing-customized");
        }
    }

    public void execute( String command ) {
        if (this.startEditingAction != null) {
            this.startEditingAction.getExtension().execute(command);
        }
    }

    public boolean isEnabled() {
        if (this.landRegistryViewerExtension.isMyLayerActive()) {
            return true;
        }
        return this.startEditingAction.isEnabled();
    }

    public boolean isVisible() {
        if (this.landRegistryViewerExtension.isMyLayerActive()) {
            return false;
        }
        return this.startEditingAction.isVisible();
    }

}
