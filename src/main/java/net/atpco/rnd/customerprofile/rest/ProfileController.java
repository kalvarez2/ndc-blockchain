package net.atpco.rnd.customerprofile.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.atpco.rnd.customerprofile.domain.CreateProfileRequest;
import net.atpco.rnd.customerprofile.service.ProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
	private final ProfileService profileService;

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "PONG";
	}

	@RequestMapping(value = "/carrierProfile/{carrierPublicKey}/{carrier}", method = RequestMethod.GET)
	public Map<String, String> getCarrierProfile(@PathVariable("carrierPublicKey") String carrierPublicKey, @PathVariable("carrier") String carrier) {
		return profileService.getFeedCarrierProfile(carrierPublicKey, carrier);
	}

	@RequestMapping(value = "/basicCarrierProfile", method = RequestMethod.GET)
	public Map<String, String> getBasicCarrierProfile(String carrierPublicKey, String carrier) {
		return profileService.getCarrierProfile(carrierPublicKey, carrier);
	}

	@RequestMapping(value = "/carrierProfile", method = RequestMethod.PUT)
	public Map<String, String> updateCarrierProfile(@RequestBody CreateProfileRequest request) {
		profileService.updateCarrierProfile(request.getCarrierPublicKey(), request.getCarrier(), request.getNewValues());
		return getCarrierProfile(request.getCarrierPublicKey(), request.getCarrier());
	}
	
	@RequestMapping(value = "/carrierProfile", method = RequestMethod.POST)
	public String createCarrierProfile(@RequestBody CreateProfileRequest request) {
		return profileService.createCarrierProfile(request.getCarrierPublicKey(), request.getCarrier(), request.getNewValues());
	}

	@RequestMapping(value = "/createFeed", method = RequestMethod.GET)
	public String createFeed( @PathVariable("carrier") String carrier) {
		return profileService.createFeed(carrier);
	}

	
}
