package operation;

import java.util.List;

import entity.Request;

public interface IViewer {
	/**
	 * @param request
	 * @return 包含每个请求的顶踩星的值
	 */
	public List view(Request request);
}
