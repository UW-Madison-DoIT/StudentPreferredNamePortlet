package edu.wisc.portlet.preferred.service;

import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.UnleashContext;
import no.finn.unleash.util.UnleashConfig;
import org.springframework.stereotype.Component;

@Component
public class UnleashService {

  UnleashConfig unleashConfig = UnleashConfig.builder()
    .appName("preferred-name")
    .instanceId("PyLv_n7aPmgqMWTua6Fd")
    .unleashAPI("https://git.doit.wisc.edu/api/v4/feature_flags/unleash/8855")
    .synchronousFetchOnInitialisation(true)
    .build();

  Unleash unleash = new DefaultUnleash(unleashConfig);

  /**
   * Returns true if the given feature flag is enabled for the given user, false otherwise;
   * returns the default value if unknown.
   * @param flagName
   * @param eppn
   * @return
   */
  public boolean featureFlagEnabledFor(String flagName, String eppn, boolean defaultValue) {

    UnleashContext context = UnleashContext.builder()
      .userId(eppn).build();

    return unleash.isEnabled(flagName, context, defaultValue);
  }

}
