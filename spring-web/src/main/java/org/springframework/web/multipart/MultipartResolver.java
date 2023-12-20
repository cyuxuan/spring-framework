/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.multipart;

import javax.servlet.http.HttpServletRequest;

/**
 * A strategy interface for multipart file upload resolution in accordance
 * with <a href="https://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>.
 * Implementations are typically usable both within an application context
 * and standalone.
 *
 * https://www.ietf.org/rfc/rfc1867.txt
 * 这是各一个协议
 * 根据<a href="https://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>的多部分文件上传协议的策略接口。
 * 实现通常既可在应用程序上下文中使用,也可独立使用。
 *
 * <p>There are two concrete implementations included in Spring, as of Spring 3.1:
 * 从spring 3.1开始有两个实现
 * <ul>
 * <li>{@link org.springframework.web.multipart.commons.CommonsMultipartResolver}
 * for Apache Commons FileUpload
 * <li>{@link org.springframework.web.multipart.support.StandardServletMultipartResolver}
 * for the Servlet 3.0+ Part API
 * </ul>
 *
 * <p>There is no default resolver implementation used for Spring
 * Spring没有默认的解析器实现
 * {@link org.springframework.web.servlet.DispatcherServlet DispatcherServlets},
 * as an application might choose to parse its multipart requests itself. To define
 * an implementation, create a bean with the id "multipartResolver" in a
 * {@link org.springframework.web.servlet.DispatcherServlet DispatcherServlet's}
 * application context. Such a resolver gets applied to all requests handled
 * by that {@link org.springframework.web.servlet.DispatcherServlet}.
 *
 * <p>对于Spring {@link org.springframework.web.servlet.DispatcherServlet DispatcherServlets}来说,
 * 没有使用默认的解析器实现来解析其多部分请求,因为应用程序可能会选择自己解析其多部分请求。
 * 要定义一个实现,可以在{@link org.springframework.web.servlet.DispatcherServlet DispatcherServlet}的应用程序上下文中创建
 * 一个id为“multipartResolver”的bean。
 * 这样的解析器会应用于所有由该{@link org.springframework.web.servlet.DispatcherServlet}处理的请求。

 *
 * <p>If a {@link org.springframework.web.servlet.DispatcherServlet} detects a
 * multipart request, it will resolve it via the configured {@link MultipartResolver}
 * and pass on a wrapped {@link javax.servlet.http.HttpServletRequest}. Controllers
 * can then cast their given request to the {@link MultipartHttpServletRequest}
 * interface, which allows for access to any {@link MultipartFile MultipartFiles}.
 * Note that this cast is only supported in case of an actual multipart request.
 *
 * <p>如果一个{@link org.springframework.web.servlet.DispatcherServlet}检测到一个多部分请求,
 * 它会通过配置的{@link MultipartResolver}解析这个请求,并传递一个包装过的{@link javax.servlet.http.HttpServletRequest}。
 * 控制器然后可以把它们得到的请求转换成{@link MultipartHttpServletRequest}接口,这个接口允许访问任何{@link MultipartFile MultipartFiles}。
 * 注意这个转换只在一个实际的多部分请求的情况下才被支持。
 *
 *
 * <pre class="code">
 * public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
 *   MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
 *   MultipartFile multipartFile = multipartRequest.getFile("image");
 *   ...
 * }</pre>
 *
 * Instead of direct access, command or form controllers can register a
 * {@link org.springframework.web.multipart.support.ByteArrayMultipartFileEditor}
 * or {@link org.springframework.web.multipart.support.StringMultipartFileEditor}
 * with their data binder, to automatically apply multipart content to form
 * bean properties.
 *
 * 作为直接访问的替代,命令或表单控制器可以在它们的数据绑定器中注册一个
 * {@link org.springframework.web.multipart.support.ByteArrayMultipartFileEditor} 或
 * {@link org.springframework.web.multipart.support.StringMultipartFileEditor}
 * 来自动将多部分内容应用到表单bean属性中。
 *
 *
 * <p>As an alternative to using a {@link MultipartResolver} with a
 * {@link org.springframework.web.servlet.DispatcherServlet},
 * a {@link org.springframework.web.multipart.support.MultipartFilter} can be
 * registered in {@code web.xml}. It will delegate to a corresponding
 * {@link MultipartResolver} bean in the root application context. This is mainly
 * intended for applications that do not use Spring's own web MVC framework.
 *
 * <p>作为使用{@link MultipartResolver}与{@link org.springframework.web.servlet.DispatcherServlet}的替代方法,
 * 可以在{@code web.xml}中注册{@link org.springframework.web.multipart.support.MultipartFilter}。
 * 它会把请求委托给根应用上下文中的相应的{@link MultipartResolver} bean。
 * 这主要是为不使用Spring自身web MVC框架的应用程序设计的。
 *
 *
 * <p>Note: There is hardly ever a need to access the {@link MultipartResolver}
 * itself from application code. It will simply do its work behind the scenes,
 * making {@link MultipartHttpServletRequest MultipartHttpServletRequests}
 * available to controllers.
 *
 * <p>注意:应用程序代码几乎没有直接访问{@link MultipartResolver}本身的需要。
 * 它只是在幕后默默地完成自己的工作,使{@link MultipartHttpServletRequest MultipartHttpServletRequests}可用于控制器。
 *
 *
 * @author Juergen Hoeller
 * @author Trevor D. Cook
 * @since 29.09.2003
 * @see MultipartHttpServletRequest
 * @see MultipartFile
 * @see org.springframework.web.multipart.commons.CommonsMultipartResolver
 * @see org.springframework.web.multipart.support.ByteArrayMultipartFileEditor
 * @see org.springframework.web.multipart.support.StringMultipartFileEditor
 * @see org.springframework.web.servlet.DispatcherServlet
 */
public interface MultipartResolver {

	/**
	 * Determine if the given request contains multipart content.
	 * <p>Will typically check for content type "multipart/form-data", but the actually
	 * accepted requests might depend on the capabilities of the resolver implementation.
	 * @param request the servlet request to be evaluated
	 * @return whether the request contains multipart content
	 * 判断给定的请求是否包含多部分内容。
	 * <p>通常会检查"multipart/form-data"类型的内容,但是被接受的请求可能依赖于解析器实现的能力。
	 * @param request 需要评估的servlet请求
	 * @return 请求是否包含多部分内容
	 *
	 */
	boolean isMultipart(HttpServletRequest request);

	/**
	 * Parse the given HTTP request into multipart files and parameters,
	 * and wrap the request inside a
	 * {@link org.springframework.web.multipart.MultipartHttpServletRequest}
	 * object that provides access to file descriptors and makes contained
	 * parameters accessible via the standard ServletRequest methods.
	 * @param request the servlet request to wrap (must be of a multipart content type)
	 * @return the wrapped servlet request
	 * @throws MultipartException if the servlet request is not multipart, or if
	 * implementation-specific problems are encountered (such as exceeding file size limits)
	 *
	 * 将给定的HTTP请求解析成多部分文件和参数,
	 * 并将请求包装在一个
	 * {@link org.springframework.web.multipart.MultipartHttpServletRequest}
	 * 对象中,该对象提供了文件描述符的访问并使包含的参数
	 * 可以通过标准的ServletRequest方法访问。
	 * @param request 需要包装的servlet请求(必须是multipart内容类型)
	 * @return 包装后的servlet请求
	 * @throws MultipartException 如果servlet请求不是multipart类型,或者
	 * 出现了特定于实现的问题(比如超过文件大小限制)
	 *
	 * @see MultipartHttpServletRequest#getFile
	 * @see MultipartHttpServletRequest#getFileNames
	 * @see MultipartHttpServletRequest#getFileMap
	 * @see javax.servlet.http.HttpServletRequest#getParameter
	 * @see javax.servlet.http.HttpServletRequest#getParameterNames
	 * @see javax.servlet.http.HttpServletRequest#getParameterMap
	 */
	MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException;

	/**
	 * Clean up any resources used for the multipart handling,
	 * like a storage for the uploaded files.
	 * @param request the request to clean up resources for
	 *
	 * 清理用于多部分处理的所有资源,
	 * 比如上传文件存储。
	 * @param request 需要清理资源的请求
	 */
	void cleanupMultipart(MultipartHttpServletRequest request);

}
