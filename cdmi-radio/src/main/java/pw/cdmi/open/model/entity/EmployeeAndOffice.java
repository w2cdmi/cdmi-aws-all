package pw.cdmi.open.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * **********************************************************
 * 员工与办事处关联的对象
 * 
 * @author liujh
 * @version iSoc Service Platform, 2015-7-18
 ***********************************************************
 */
@Data
@Entity
@Table(name = "ept_employee_and_office")
public class EmployeeAndOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;					// 员工与办事处的关联对象编号

    private String employeeId;			// 员工编号

    private String officeId;		    // 办事处编号

}
