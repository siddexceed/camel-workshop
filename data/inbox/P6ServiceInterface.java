package com.cisco.dcp.webservices.util;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cisco.asdc.common.exceptions.DCPServiceException;
import com.cisco.asdc.common.logging.DCPLogger;
import com.cisco.asdc.common.utils.AppBf;
import com.cisco.asdc.common.utils.Base64Coder;
import com.cisco.dcp.webservices.constants.DCPDMConstants;

/**
 * The Class P6ServiceInterface.
 */
public class P6ServiceInterface {

	/** The rest template. */
	private RestTemplate restTemplate;

	/**
	 * Gets the rest template.
	 * 
	 * @return the rest template
	 */
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	/**
	 * Sets the rest template.
	 * 
	 * @param restTemplate
	 *            the new rest template
	 */
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Execute p6 post.
	 * 
	 * @param serviceURL
	 *            the service url
	 * @param request
	 *            the request
	 * @return the string
	 * @throws DCPServiceException
	 *             the aSDC service exception
	 */
	public String executeP6Post(String serviceURL, String request)
			throws DCPServiceException {

		String responsePost = "";
		try {
				responsePost = executeP6PostNew(serviceURL, request);
		} catch (Exception e) {
			DCPLogger
					.errorLog(e,
							"Error in executeP6Post() function in P6ServiceInterface class");
			throw new DCPServiceException(e);
		}
		return responsePost;
	}

	public String executeP6PostNew(String serviceURL, String request)
			throws DCPServiceException {

		String responsePost = "";

		try {
			HttpHeaders entityHeadersPost = new HttpHeaders();
			entityHeadersPost.set(DCPDMConstants.CONTENT_TYPE_CAMEL_CASE,
					DCPDMConstants.JSON_FORMAT_UTF);
			String authentication = Base64Coder.encodeString(CommonUtil
					.getExternalAttribute(DCPDMConstants.P6_GENERIC_ID)
					+ ":"
					+ AppBf.decryptString(CommonUtil
							.getExternalAttribute(DCPDMConstants.P6_PASSWORD)));
			entityHeadersPost.set(DCPDMConstants.AUTHORIZATION,
					DCPDMConstants.BASIC + authentication);

			/**
			 * Fetch the consumer id from the properties file and set in the
			 * HTTP header
			 */
			HttpEntity<String> requestEntity = new HttpEntity<String>(request,
					entityHeadersPost);
			URI requestURI = new URI(serviceURL);
			DCPLogger.debugLog("request : %s", request);
			restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.exchange(requestURI,
					HttpMethod.POST, requestEntity, String.class);
			DCPLogger.debugLog("result post: %s", result.getBody());
			responsePost = result.getBody();
		} catch (Exception e) {
			DCPLogger
					.errorLog(e,
							"Error in executeP6Post() function in P6ServiceInterface class");
			throw new DCPServiceException(e);
		}
		return responsePost;
	}

	/**
	 * Execute p6 get.
	 * 
	 * @param serviceURL
	 *            the service url
	 * @return the string
	 * @throws DCPServiceException
	 *             the aSDC service exception
	 */
	public String executeP6Get(String serviceURL) throws DCPServiceException {

		String response = "";

		try {
			HttpHeaders entityHeaders = new HttpHeaders();
			entityHeaders.set(DCPDMConstants.CONTENT_TYPE_CAMEL_CASE,
					DCPDMConstants.JSON_FORMAT);
			String authentication = Base64Coder.encodeString(CommonUtil
					.getExternalAttribute(DCPDMConstants.P6_GENERIC_ID)
					+ ":"
					+ AppBf.decryptString(CommonUtil
							.getExternalAttribute(DCPDMConstants.P6_PASSWORD)));
			entityHeaders.set(DCPDMConstants.AUTHORIZATION, DCPDMConstants.BASIC
					+ authentication);

			/**
			 * Fetch the consumer id from the properties file and set in the
			 * HTTP header
			 */
			HttpEntity<String> requestEntity = new HttpEntity<String>(
					entityHeaders);
			URI requestURI = new URI(serviceURL);
			restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.exchange(requestURI,
					HttpMethod.GET, requestEntity, String.class);
			DCPLogger.debugLog("result get: %s", result.getBody());
			response = result.getBody();
		} catch (Exception e) {
			DCPLogger
					.errorLog(e,
							"Error in executeP6Get() function in P6ServiceInterface class");
			throw new DCPServiceException(e);
		}
		return response;
	}

	/**
	 * This method is used to send PUT request to P6 service.
	 * 
	 * @param serviceURL
	 *            : P6 service URL
	 * @param request
	 *            the request
	 * @return the string
	 * @throws DCPServiceException
	 *             the aSDC service exception
	 */
	public String executeP6Put(String serviceURL, String request)
			throws DCPServiceException {

		String response = "";

		try {
			HttpHeaders entityHeaders = new HttpHeaders();
			entityHeaders.set(DCPDMConstants.CONTENT_TYPE_CAMEL_CASE,
					DCPDMConstants.JSON_FORMAT);
			String authentication = Base64Coder.encodeString(CommonUtil
					.getExternalAttribute(DCPDMConstants.P6_GENERIC_ID)
					+ ":"
					+ AppBf.decryptString(CommonUtil
							.getExternalAttribute(DCPDMConstants.P6_PASSWORD)));
			entityHeaders.set(DCPDMConstants.AUTHORIZATION, DCPDMConstants.BASIC
					+ authentication);

			/**
			 * Fetch the consumer id from the properties file and set in the
			 * HTTP header
			 */
			HttpEntity<String> requestEntity = new HttpEntity<String>(request,
					entityHeaders);
			URI requestURI = new URI(serviceURL);
			DCPLogger.debugLog("request : %s", request);
			restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.exchange(requestURI,
					HttpMethod.PUT, requestEntity, String.class);
			DCPLogger.debugLog("result put: %s", result.getBody());
			response = result.getBody();
		} catch (Exception e) {
			DCPLogger
					.errorLog(e,
							"Error in executeP6Put() function in P6ServiceInterface class");
			throw new DCPServiceException(e);
		}
		return response;
	}

}
