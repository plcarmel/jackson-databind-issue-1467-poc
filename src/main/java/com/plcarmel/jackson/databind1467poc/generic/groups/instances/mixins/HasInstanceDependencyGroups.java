package com.plcarmel.jackson.databind1467poc.generic.groups.instances.mixins;

import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceDependencyGroups;

public interface HasInstanceDependencyGroups<TInput> {

  InstanceDependencyGroups<TInput> getDependencyGroups();

}
