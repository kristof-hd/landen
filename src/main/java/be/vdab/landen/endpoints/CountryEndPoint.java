package be.vdab.landen.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import be.vdab.landen.exceptions.InvalidCountryCodeException;
import be.vdab.landen.soapmessages.Country;
import be.vdab.landen.soapmessages.GetCountryRequest;
import be.vdab.landen.soapmessages.GetCountryResponse;
import be.vdab.landen.soapmessages.GetLanguagesRequest;
import be.vdab.landen.soapmessages.GetLanguagesResponse;

@Endpoint
public class CountryEndPoint {
  private static final String NAMESPACE_URI = "http://www.be.vdab.countries";
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
  public @ResponsePayload GetCountryResponse getCountry(@RequestPayload GetCountryRequest request)
  {
    if ("BE".equals(request.getCode())) {
      GetCountryResponse response = new GetCountryResponse();
      Country country = new Country();
      country.setName("Belgium");
      country.setCapital("Brussels");
      response.setCountry(country);
      return response;
    }
    //throw new IllegalArgumentException("invalid code:" + request.getCode());
    throw new InvalidCountryCodeException(request.getCode());
  }
  
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLanguagesRequest")
  public @ResponsePayload GetLanguagesResponse getLanguages(@RequestPayload GetLanguagesRequest request) {
    GetLanguagesResponse response = new GetLanguagesResponse();
    response.getLanguages().add("NL");
    response.getLanguages().add("FR");
    response.getLanguages().add("D");
    return response;
  }
  
}