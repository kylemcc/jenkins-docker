package com.vivid.docker;

import com.vivid.docker.argument.ArgumentBuilder;
import com.vivid.docker.command.DockerCommandExecutor;
import com.vivid.docker.exception.EnvironmentConfigurationException;
import com.vivid.docker.util.FieldUtil;
import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * Created by Phil Madden on 9/17/15.
 */
public class DockerBuildStep extends Builder {

    protected static final int SUCCESS = 0;
    private final String alternativeDockerHost;

    public DockerBuildStep(String alternativeDockerHost) {
        this.alternativeDockerHost = alternativeDockerHost;
    }

    public BuildImageBuildStepDescriptor getDockerConfigurationDescriptor() {
        return (BuildImageBuildStepDescriptor) Jenkins.getInstance().getDescriptor(BuildImageBuildStep.class);
    }

    public EnvVars getEnvironment(AbstractBuild build, BuildListener listener) throws EnvironmentConfigurationException {
        try {
            EnvVars envVars = build.getEnvironment(listener);

            if (StringUtils.isNotBlank(alternativeDockerHost)) {
                envVars.put("DOCKER_HOST", FieldUtil.getMacroReplacedFieldValue(alternativeDockerHost, envVars));
            } else if(StringUtils.isNotBlank(getDockerConfigurationDescriptor().getDockerHost())) {
                envVars.put("DOCKER_HOST", getDockerConfigurationDescriptor().getDockerHost());
            }
            return envVars;
        } catch (IOException e) {
            throw new EnvironmentConfigurationException(e);
        } catch (InterruptedException e) {
            throw new EnvironmentConfigurationException(e);
        }
    }

    public DockerCommandExecutor getCommand(ArgumentBuilder argumentBuilder, EnvVars environment) {
        return new DockerCommandExecutor(argumentBuilder, environment);
    }

    public String getAlternativeDockerHost() {
        return alternativeDockerHost;
    }
}
