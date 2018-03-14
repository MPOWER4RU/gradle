/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.language.nativeplatform.internal.registry;

import org.gradle.initialization.RootBuildLifecycleListener;
import org.gradle.internal.event.ListenerManager;
import org.gradle.internal.service.ServiceRegistration;
import org.gradle.internal.service.scopes.AbstractPluginServiceRegistry;
import org.gradle.language.cpp.internal.NativeDependencyCache;
import org.gradle.language.internal.DefaultNativeComponentFactory;
import org.gradle.language.nativeplatform.internal.incremental.DefaultCompilationStateCacheFactory;
import org.gradle.language.nativeplatform.internal.incremental.DefaultIncrementalCompilerBuilder;
import org.gradle.language.nativeplatform.internal.incremental.IncrementalCompileFilesFactory;
import org.gradle.language.nativeplatform.internal.incremental.sourceparser.CachingCSourceParser;
import org.gradle.language.nativeplatform.internal.toolchains.DefaultToolChainSelector;

public class NativeLanguageServices extends AbstractPluginServiceRegistry {
    @Override
    public void registerGlobalServices(ServiceRegistration registration) {
        registration.add(Dump.class);
    }

    public static class Dump implements RootBuildLifecycleListener {
        public Dump(ListenerManager listenerManager) {
            listenerManager.addListener(this);
        }

        @Override
        public void afterStart() {
        }

        @Override
        public void beforeComplete() {
            IncrementalCompileFilesFactory.dump();
        }
    }

    @Override
    public void registerGradleServices(ServiceRegistration registration) {
        registration.add(DefaultCompilationStateCacheFactory.class);
        registration.add(CachingCSourceParser.class);
    }

    @Override
    public void registerBuildServices(ServiceRegistration registration) {
        registration.add(NativeDependencyCache.class);
    }

    @Override
    public void registerProjectServices(ServiceRegistration registration) {
        registration.add(DefaultIncrementalCompilerBuilder.class);
        registration.add(DefaultToolChainSelector.class);
        registration.add(DefaultNativeComponentFactory.class);
    }
}
