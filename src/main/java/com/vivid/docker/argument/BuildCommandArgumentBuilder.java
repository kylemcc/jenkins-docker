package com.vivid.docker.argument;

import hudson.util.ArgumentListBuilder;

import java.io.File;

/**
 * Created by Phil Madden on 9/10/15.
 */
public class BuildCommandArgumentBuilder extends ImageArgumentBuilder<BuildCommandArgumentBuilder> {
    protected boolean hasSpecifiedFile = false;
    protected boolean hasBuildContext = false;
    private String buildContext;

    public BuildCommandArgumentBuilder() {
        super("build");
    }

    public final ArgumentListBuilder build() {
        if(!hasBuildContext) {
            argumentListBuilder.add(".");
        } else {
            argumentListBuilder.add(buildContext);
        }
        return argumentListBuilder;
    }

    public BuildCommandArgumentBuilder tag(String tag) {
        if(tag != null && !tag.isEmpty()) {
            argumentListBuilder.addKeyValuePair("--", "tag", wrapInQuotes(tag), false);
        }
        return this;
    }

    public final BuildCommandArgumentBuilder file(String file) {
        if(file != null && !file.isEmpty()) {
            buildContext = new File(file).getParent();
            argumentListBuilder.addKeyValuePair("--", "file", wrapInQuotes(file), false);
            hasSpecifiedFile = true;
        }
        return this;
    }

    public final BuildCommandArgumentBuilder disbaleContentTrust(boolean value) {
        if(value) {
            argumentListBuilder.add("--disable-content-trust");
        }
        return this;
    }

    public final BuildCommandArgumentBuilder forceRemove(boolean value) {
        if(value) {
            argumentListBuilder.add("--force-rm");
        }
        return this;
    }

    public final BuildCommandArgumentBuilder remove(boolean value) {
        if(value) {
            argumentListBuilder.add("--rm");
        }
        return this;
    }

    public final BuildCommandArgumentBuilder noCache(boolean value) {
        if(value) {
            argumentListBuilder.add("--no-cache");
        }
        return this;
    }

    public final BuildCommandArgumentBuilder pull(boolean value) {
        if(value) {
            argumentListBuilder.add("--pull");
        }
        return this;
    }

    public final BuildCommandArgumentBuilder buildContext(String buildContext) {
        if (buildContext != null && !buildContext.isEmpty()) {
            this.buildContext = buildContext;
            hasBuildContext = true;
        }
        return this;
    }
}
