package xin.yangshuai.spring.jdbc;

/**
 * Department
 *
 * @author shuai
 * @date 2018/12/14
 */
public class Department {
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
