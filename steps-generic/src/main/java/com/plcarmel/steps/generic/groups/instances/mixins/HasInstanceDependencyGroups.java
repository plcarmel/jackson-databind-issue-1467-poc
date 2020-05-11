package com.plcarmel.steps.generic.groups.instances.mixins;

import com.plcarmel.steps.generic.groups.instances.InstanceDependencyGroups;

public interface HasInstanceDependencyGroups<TInput> {

  InstanceDependencyGroups<TInput> getDependencyGroups();

}
