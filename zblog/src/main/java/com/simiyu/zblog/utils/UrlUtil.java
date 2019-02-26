package com.simiyu.zblog.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

public class UrlUtil {

	public static String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
			String enc = httpServletRequest.getCharacterEncoding();
			if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
			}
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
			
			return pathSegment;
	}
}