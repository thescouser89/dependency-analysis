package org.jboss.da.common.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * This class represents the configuration for pnc in the unified config. We
 * leave it as empty since we don't care about it for now.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PNCGroupWrapper extends AbstractModuleGroup {
}