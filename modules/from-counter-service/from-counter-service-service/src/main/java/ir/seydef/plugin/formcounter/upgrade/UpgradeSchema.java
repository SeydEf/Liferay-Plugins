package ir.seydef.plugin.formcounter.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import ir.seydef.plugin.formcounter.upgrade.v1_0_1.UpgradeFormCounterRule;

import org.osgi.service.component.annotations.Component;

/**
 * @author S.Abolfazl Eftekhari
 */
@Component(
	immediate = true,
	service = {UpgradeSchema.class, UpgradeStepRegistrator.class}
)
public class UpgradeSchema implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("1.0.0", "1.0.1", new UpgradeFormCounterRule());
	}

}