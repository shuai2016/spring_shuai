package xin.yangshuai.spring.beans;

/**
 * Hello
 *
 * @author shuai
 * @date 2018/12/13
 */
public class Hello {
	private String name;

	public Hello() {
		System.out.println("Hello's Constructor...");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("Hello's setName...");
		this.name = name;
	}

	public void init2(){
		System.out.println("Hello's init...");
	}

	public void destroy(){
		System.out.println("Hello's destroy...");
	}

	@Override
	public String toString() {
		return "Hello{" +
				"name='" + name + '\'' +
				'}';
	}
}
