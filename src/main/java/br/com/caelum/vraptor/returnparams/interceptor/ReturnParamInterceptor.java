package br.com.caelum.vraptor.returnparams.interceptor;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.interceptor.ExecuteMethodInterceptor;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.interceptor.ParametersInstantiatorInterceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts(before=ExecuteMethodInterceptor.class, after = ParametersInstantiatorInterceptor.class)
public class ReturnParamInterceptor implements Interceptor{

	private final MethodInfo info;
	private final Result result;
	private final ParameterNameProvider nameProvider;

	public ReturnParamInterceptor(Result result, MethodInfo info, ParameterNameProvider nameProvider) {
		this.result = result;
		this.info = info;
		this.nameProvider = nameProvider;
	}
	
	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object instance) throws InterceptionException {
		Object[] parameters = info.getParameters();
		String[] names = nameProvider.parameterNamesFor(method.getMethod());
		for(int i=0;i< names.length;i++) {
			result.include(names[i], parameters[i]);
		}
		stack.next(method, instance);
	}
	
}
