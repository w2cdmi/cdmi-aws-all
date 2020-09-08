import request from '@/utils/request';

//获得学校的基本信息
export async function getSchool(schoolid) {
    return request(`/api/school/v1/schools/${schoolid}`);
}

//编辑学校的基本信息
export async function editSchool(schoolid, school) {
    return request(`/api/school/v1/schools/${schoolid}`, {
        method: 'PUT',
        body: school,
    });
}

//获取学校的所有年级列表(初一，初二，初三)
export async function getGrades(schoolid) {
    return request(`/api/school/v1/schools/${schoolid}/grades`);
}

//获取学校所有的年届信息（初中2017届，初中2018届）
export async function getYearGrades(schoolid) {
    return request(`/api/school/v1/schools/${schoolid}/yeargrades`);
}

//为学校创建年届班级列表
export async function createClass(schoolid, sch_class) {
    return request(`/api/school/v1/schools/${schoolid}/classes/class`, {
        method: 'POST',
        body: sch_class,
    });
}

//获取当前仍在校的年级下的班级列表
export async function getClass(schoolid, grade) {
    return request(`/api/school/v1/schools/${schoolid}/classes?grade=${grade}`);
}

//获取指定教师所负责的当前在校班级列表
export async function getCurrentClass(schoolid, teacherid) {
    return request(`/api/school/v1/schools/${schoolid}/classes/current?teacherid=${teacherid}`);
}

//为学校指定班级分配班主任
export async function setAdviser4Class(schoolid, classid, teacherid) {
    return request(`/api/school/v1/schools/${schoolid}/classes/${classid}/adviser`, {
        method: 'PUT',
        body: teacherid,
    });
}

//为学校指定班级分配班主任
export async function setAdviser4Class(schoolid, classid, teacherid, courseid) {
    return request(`/api/school/v1/schools/${schoolid}/classes/${classid}/adviser`, {
        method: 'PUT',
        body: {
            "teacherid": teacherid,
            "courseid": courseid,
        }
    });
}

//创建学校的课程科目
export async function createCourse(schoolid, course) {
    return request(`/api/school/v1/schools/${schoolid}/courses/course`, {
        method: 'POST',
        body: course,
    });
}

//编辑学校的课程科目
export async function editCourse(schoolid, courseid, course) {
    return request(`/api/school/v1/schools/${schoolid}/courses/${courseid}`, {
        method: 'PUT',
        body: course,
    });
}

//关闭指定的课程科目
export async function DisabledCourse(schoolid, courseid) {
    return request(`/api/school/v1/schools/${schoolid}/courses/${courseid}/disabled`, {
        method: 'PUT',
    });
}

//开启指定的课程科目
export async function DisabledCourse(schoolid, courseid) {
    return request(`/api/school/v1/schools/${schoolid}/courses/${courseid}/enabled`, {
        method: 'PUT',
    });
}

//删除指定的学校课程科目
export async function deleteCourse(schoolid, courseid) {
    return request(`/api/school/v1/schools/${schoolid}/courses/${courseid}`, {
        method: 'DELETE',
    });
}

//创建学校的教师信息
export async function createTeacher(schoolid, teacher) {
    return request(`/api/school/v1/schools/${schoolid}/teachers/teacher`, {
        method: 'POST',
        body: teacher,
    });
}

//编辑学校的教师信息
export async function editTeacher(schoolid, teacher) {
    return request(`/api/school/v1/schools/${schoolid}/teachers/teacher`, {
        method: 'PUT',
        body: teacher,
    });
}

//获取指定教师基本信息
export async function getTeacher(schoolid, teacherid) {
    return request(`/api/school/v1/schools/${schoolid}/teachers/${teacherid}`);
}

//设置指定教师的在职状态
export async function setTeacherStatus(schoolid, teacherid) {
    return request(`/api/school/v1/schools/${schoolid}/teachers/${teacherid}/status`, {
        method: 'PUT',
        body: status,
    });
}

//删除指定教师的信息
export async function deleteTeacher(schoolid, teacherid) {
    return request(`/api/school/v1/schools/${schoolid}/teachers/${teacherid}`, {
        method: 'DELETE',
    });
}

//获取指定年届班级下的老师列表
export async function getTeachersInClass(schoolid, classid) {
    return request(`/api/school/v1/schools/${schoolid}/classes/${classid}/teachers`);
}

//获取指定学校的老师数量
export async function getTeacherCount(schoolid) {
    return request(`/api/school/v1/schools/${schoolid}/teachers/count`);
}