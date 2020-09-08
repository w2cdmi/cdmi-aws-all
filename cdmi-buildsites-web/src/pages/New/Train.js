import React, { PureComponent, Fragment } from 'react';
import Media from 'react-media';
import { connect } from 'dva';
import moment from 'moment';
import {
    Drawer,
    Row,
    Col,
    Card,
    Form,
    Input,
    Select,
    Icon,
    Checkbox,
    Button,
    InputNumber,
    DatePicker,
    Modal,
    message,
    Badge,
    Steps,
    Radio,
    Upload,
    Divider,
    Tabs,
    Slider,
    Table,
} from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './TableList.less';
import { SSL_OP_SINGLE_DH_USE } from 'constants';

const FormItem = Form.Item;
const { Step } = Steps;
const { TextArea } = Input;
const { Option } = Select;
const RadioGroup = Radio.Group;
const TabPane = Tabs.TabPane;

const getValue = obj =>
    Object.keys(obj)
        .map(key => obj[key])
        .join(',');
const statusMap = ['default', 'processing', 'success', 'error'];
const status = ['草稿', '已分配班组', '进行中', '已完成'];

const CreateForm = Form.create()(props => {
    const { selectedRows, data, loading, newScheduleVisible, form, handleAdd, handleNewScheduleVisible, isMobile } = props;
    const okHandle = () => {
        form.validateFields((err, fieldsValue) => {
            if (err) return;
            form.resetFields();
            handleAdd(fieldsValue);
        });
    };
    const handleBloodChange = (value) => {
        console.log(`selected ${value}`);
    };
    const width = isMobile ? `100%` : 720;
    const columns = [
        {
            title: '课程名称',
            dataIndex: 'name',
        },
    ];
    const paginationProps = {
        ...data.pagination,
        showQuickJumper: true,
        pageSize: 5,
        pageSizeOptions: [5, 10, 15, 20],
    };
    return (
        <Drawer
            title="添加培训计划"
            width={width}
            onClose={() => handleNewScheduleVisible()}
            visible={newScheduleVisible}
            style={{
                overflow: 'auto',
                height: 'calc(100% - 108px)',
                paddingBottom: '108px',
            }}
        >
            <Form layout="vertical">
                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                    <Col sm={8} md={24}>
                        <Form.Item label="课程类型过滤">
                            <Select placeholder="选择类型">
                                <Option value="private">水泥</Option>
                                <Option value="public">钢材</Option>
                            </Select>
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                    <Col sm={8} md={24}>
                        <Form.Item label="选择课程">
                            {form.getFieldDecorator('course', {
                                rules: [{ required: true, message: '任务名称为必须填写项' }],
                            })(<Table
                                showHeader={false}
                                rowSelection={{ type: 'radio' }}
                                selectedRows={selectedRows}
                                loading={loading}
                                dataSource={data.list}
                                columns={columns}
                                pagination={paginationProps}
                                style={{ borderTop: 1, borderTopColor: '#e8e8e8', borderTopStyle: 'solid' }}
                            />)}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>
                    <Col span={24}>
                        <Form.Item label="设置下一次培训时间">
                            {form.getFieldDecorator('group', {
                                rules: [{ required: true, message: '任务名称为必须填写项' }],
                            })(
                                <DatePicker style={{ width: '100%' }} placeholder="请输入日期" />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
            <div
                style={{
                    position: 'absolute',
                    left: 0,
                    bottom: 0,
                    width: '100%',
                    borderTop: '1px solid #e9e9e9',
                    padding: '10px 16px',
                    background: '#fff',
                    textAlign: 'right',
                }}
            >
                <Button onClick={() => handleNewScheduleVisible()} style={{ marginRight: 8 }}>
                    Cancel
            </Button>
                <Button onClick={okHandle} type="primary">
                    Submit
            </Button>
            </div>
        </Drawer>
    );
});

const CreateNewCourseForm = Form.create()(props => {
    const { modalVisible, form, handleAdd, handleModalVisible, isMobile } = props;
    const okHandle = () => {
        form.validateFields((err, fieldsValue) => {
            if (err) return;
            form.resetFields();
            handleAdd(fieldsValue);
        });
    };
    const handleBloodChange = (value) => {
        console.log(`selected ${value}`);
    };
    const width = isMobile ? `100%` : 720;

    return (
        <Drawer
            title="添加培训课程"
            width={width}
            onClose={() => handleModalVisible()}
            visible={modalVisible}
            style={{
                overflow: 'auto',
                height: 'calc(100% - 108px)',
                paddingBottom: '108px',
            }}
        >
            <Form layout="vertical">
                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                    <Col sm={8} md={24}>
                        <Form.Item label="课程类型">
                            {form.getFieldDecorator('type', {
                                rules: [{ required: true, message: '任务名称为必须填写项' }],
                            })(<Select placeholder="选择类型">
                                <Option value="private">水泥</Option>
                                <Option value="public">钢材</Option>
                            </Select>)}
                        </Form.Item>
                    </Col>
                    <Col sm={8} md={24}>
                        <Form.Item label="课程名称">
                            {form.getFieldDecorator('name', {
                                rules: [{ required: true, message: '任务名称为必须填写项' }],
                            })(<Input placeholder="输入任务名称" />)}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                    <Col sm={8} md={24}>
                        <Form.Item label="课程时长">
                            {form.getFieldDecorator('duration', {
                                rules: [{ required: true, message: '任务名称为必须填写项' }],
                            })(<Input placeholder="格式：整数或小数" addonAfter='小时' />)}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>
                    <Col span={24}>
                        <Form.Item label="课程描述">
                            {form.getFieldDecorator('description')(<Input.TextArea rows={4} placeholder="请输入课程描述" />)}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>
                    <Col span={24}>
                        <Form.Item label="设置第一次培训计划">
                            {form.getFieldDecorator('group')(
                                <DatePicker style={{ width: '100%' }} placeholder="请输入更新日期" />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                    <Col sm={8} md={24}>
                        <Form.Item label="上传在线课程视频">
                            {/* {form.getFieldDecorator('approver', {
                                rules: [{ required: true, message: 'Please enter user name' }],
                            })(<Input placeholder="上传照片" />)} */}
                            <PicturesWall />
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
            <div
                style={{
                    position: 'absolute',
                    left: 0,
                    bottom: 0,
                    width: '100%',
                    borderTop: '1px solid #e9e9e9',
                    padding: '10px 16px',
                    background: '#fff',
                    textAlign: 'right',
                }}
            >
                <Button onClick={() => handleModalVisible()} style={{ marginRight: 8 }}>
                    Cancel
            </Button>
                <Button onClick={okHandle} type="primary">
                    Submit
            </Button>
            </div>
        </Drawer>
    );
});

/** 创建上传身份证照片墙 */
@Form.create()
class PicturesWall extends React.Component {
    state = {
        previewVisible: false,
        previewImage: '',
        fileList: [{
            uid: '-1',
            name: 'xxx.png',
            status: 'done',
            url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
        }],
    };

    handleCancel = () => this.setState({ previewVisible: false })

    handlePreview = (file) => {
        this.setState({
            previewImage: file.url || file.thumbUrl,
            previewVisible: true,
        });
    }
    handleChange = ({ fileList }) => this.setState({ fileList })

    render() {
        const { previewVisible, previewImage, fileList } = this.state;
        const uploadButton = (
            <div>
                <Icon type="plus" />
                <div className="ant-upload-text">上传图纸</div>
            </div>
        );
        return (
            <div className="clearfix">
                <Upload
                    action="//jsonplaceholder.typicode.com/posts/"
                    listType="picture-card"
                    fileList={fileList}
                    onPreview={this.handlePreview}
                    onChange={this.handleChange}
                >
                    {fileList.length >= 3 ? null : uploadButton}
                </Upload>
                <Modal visible={previewVisible} footer={null} onCancel={this.handleCancel}>
                    <img alt="example" style={{ width: '100%' }} src={previewImage} />
                </Modal>
            </div>
        );
    }
}


/* eslint react/no-multi-comp:0 */
@connect(({ rule, loading }) => ({
    rule,
    trains: rule.data,
    loading: loading.models.rule,
}))
@Form.create()
class Train extends PureComponent {
    constructor(props) {
        super(props);
    };

    state = {
        modalVisible: false,
        newScheduleVisible: false,
        updateModalVisible: false,
        selectedRows: [],
        formValues: {},
        stepFormValues: {},
    };

    columns = !this.props.isMobile ? [
        {
            title: '培训时间',
            dataIndex: 'time',
            sorter: true,
            render: val => <span>{moment(val).format('YYYY-MM-DD')}</span>,
        },
        {
            title: '课程名称',
            dataIndex: 'name',
        },
        {
            title: '课程类型',
            dataIndex: 'type',
        },
        {
            title: '参与人数',
            dataIndex: 'count',
        },
        {
            title: '操作',
            render: (text, record) => (
                <Fragment>
                    <a onClick={() => this.handleModalVisible(true, record)}>详细</a>
                </Fragment>
            ),
        },
    ] : [
            {
                title: '课程名称',
                dataIndex: 'name',
            },
            {
                title: '操作',
                render: (text, record) => (
                    <Fragment>
                        <a onClick={() => this.handleModalVisible(true, record)}>详细</a>
                    </Fragment>
                ),
            },
        ];

    course_columns = !this.props.isMobile ? [
        {
            title: '课程名称',
            dataIndex: 'name',
        },
        {
            title: '课程类型',
            dataIndex: 'type',
        },
        {
            title: '课程时长',
            dataIndex: 'duration',
        },
        {
            title: '操作',
            render: (text, record) => (
                <Fragment>
                    <a onClick={() => this.handleModalVisible(true, record)}>详细</a>
                </Fragment>
            ),
        },
    ] : [
            {
                title: '课程名称',
                dataIndex: 'name',
            },
            {
                title: '操作',
                render: (text, record) => (
                    <Fragment>
                        <a onClick={() => this.handleModalVisible(true, record)}>详细</a>
                    </Fragment>
                ),
            },
        ];

    componentDidMount() {
        const { dispatch } = this.props;
        dispatch({
            type: 'rule/fetch',
        });
    }

    handleStandardTableChange = (pagination, filtersArg, sorter) => {
        const { dispatch } = this.props;
        const { formValues } = this.state;

        const filters = Object.keys(filtersArg).reduce((obj, key) => {
            const newObj = { ...obj };
            newObj[key] = getValue(filtersArg[key]);
            return newObj;
        }, {});

        const params = {
            currentPage: pagination.current,
            pageSize: pagination.pageSize,
            ...formValues,
            ...filters,
        };
        if (sorter.field) {
            params.sorter = `${sorter.field}_${sorter.order}`;
        }

        dispatch({
            type: 'rule/fetch',
            payload: params,
        });
    };

    handleFormReset = () => {
        const { form, dispatch } = this.props;
        form.resetFields();
        this.setState({
            formValues: {},
        });
        dispatch({
            type: 'rule/fetch',
            payload: {},
        });
    };

    handleMenuClick = e => {
        const { dispatch } = this.props;
        const { selectedRows } = this.state;

        if (selectedRows.length === 0) return;
        switch (e.key) {
            case 'remove':
                dispatch({
                    type: 'rule/remove',
                    payload: {
                        key: selectedRows.map(row => row.key),
                    },
                    callback: () => {
                        this.setState({
                            selectedRows: [],
                        });
                    },
                });
                break;
            default:
                break;
        }
    };

    handleSelectRows = rows => {
        this.setState({
            selectedRows: rows,
        });
    };

    handleSearch = e => {
        e.preventDefault();

        const { dispatch, form } = this.props;

        form.validateFields((err, fieldsValue) => {
            if (err) return;

            const values = {
                ...fieldsValue,
                updatedAt: fieldsValue.updatedAt && fieldsValue.updatedAt.valueOf(),
            };

            this.setState({
                formValues: values,
            });

            dispatch({
                type: 'rule/fetch',
                payload: values,
            });
        });
    };

    handleModalVisible = (flag, record) => {
        this.setState({
            modalVisible: !!flag,
            stepFormValues: record || {},
        });
    };

    handleNewScheduleVisible = (flag, record) => {
        this.setState({
            newScheduleVisible: !!flag,
            stepFormValues: record || {},
        });
    };

    handleAdd = fields => {
        const { dispatch } = this.props;
        dispatch({
            type: 'rule/add',
            payload: {
                desc: fields.desc,
            },
        });

        message.success('添加成功');
        this.handleModalVisible();
    };

    handleUpdate = fields => {
        const { dispatch } = this.props;
        dispatch({
            type: 'rule/update',
            payload: {
                name: fields.name,
                desc: fields.desc,
                key: fields.key,
            },
        });

        message.success('配置成功');
        this.handleUpdateModalVisible();
    };

    render() {
        const {
            rule: { data },
            loading,
            isMobile,
        } = this.props;
        const { selectedRows, modalVisible, newScheduleVisible, updateModalVisible, stepFormValues } = this.state;
        // const menu = (
        //     <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        //         <Menu.Item key="remove">删除</Menu.Item>
        //         <Menu.Item key="approval">批量审批</Menu.Item>
        //     </Menu>
        // );
        const parentMethods = {
            handleAdd: this.handleAdd,
            handleModalVisible: this.handleModalVisible,
        };
        const updateMethods = {
            handleUpdateModalVisible: this.handleUpdateModalVisible,
            handleUpdate: this.handleUpdate,
        };
        const newScheduleMethods = {
            handleNewScheduleVisible: this.handleNewScheduleVisible,
            handleUpdate: this.handleUpdate,
        };
        return (
            <PageHeaderWrapper>
                <Card bordered={false}>
                    <Tabs type="card" tabBarGutter={8}>
                        <TabPane tab="培训计划" key="1" >
                            <div className={styles.tableList}>
                                <div className={styles.tableListOperator}>
                                    <Button icon="plus" type="primary" onClick={() => this.handleNewScheduleVisible(true)}>
                                        添加新计划
              </Button>
                                    {selectedRows.length > 0 && (
                                        <span>
                                            <Button>批量删除</Button>
                                            {/* <Dropdown overlay={menu}>
                                        <Button>
                                            更多操作 <Icon type="down" />
                                        </Button>
                                    </Dropdown> */}
                                        </span>
                                    )}
                                </div>
                                <StandardTable
                                    selectedRows={selectedRows}
                                    loading={loading}
                                    data={data}
                                    columns={this.columns}
                                    onSelectRow={this.handleSelectRows}
                                    onChange={this.handleStandardTableChange}
                                />
                            </div>
                        </TabPane>
                        <TabPane tab="培训课程" key="2">
                            <div className={styles.tableList}>
                                <div className={styles.tableListOperator}>
                                    <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true)}>
                                        添加新课程
              </Button>
                                    {selectedRows.length > 0 && (
                                        <span>
                                            <Button>批量删除</Button>
                                            {/* <Dropdown overlay={menu}>
                                        <Button>
                                            更多操作 <Icon type="down" />
                                        </Button>
                                    </Dropdown> */}
                                        </span>
                                    )}
                                </div>
                                <StandardTable
                                    selectedRows={selectedRows}
                                    loading={loading}
                                    data={data}
                                    columns={this.course_columns}
                                    onSelectRow={this.handleSelectRows}
                                    onChange={this.handleStandardTableChange}
                                />
                            </div>
                        </TabPane>
                    </Tabs>
                    <CreateForm {...newScheduleMethods} newScheduleVisible={newScheduleVisible} isMobile={isMobile} data={data} selectedRows={selectedRows} loading={loading} columns={this.course_columns} />
                    <CreateNewCourseForm {...parentMethods} modalVisible={modalVisible} isMobile={isMobile} />
                    {stepFormValues && Object.keys(stepFormValues).length ? (
                        <CreateForm
                            {...parentMethods}
                            modalVisible={modalVisible}
                            values={stepFormValues}
                            isMobile={isMobile}
                        />
                    ) : null}
                    {/* {stepFormValues && Object.keys(stepFormValues).length ? (
                    <UpdateForm
                        {...updateMethods}
                        updateModalVisible={updateModalVisible}
                        values={stepFormValues}
                        isMobile={isMobile}
                    />
                ) : null} */}
                </Card>
            </PageHeaderWrapper>
        );
    }
}

// export default Worker;
export default connect()(props => (
    <Media query="(max-width: 599px)">
        {isMobile => <Train {...props} isMobile={isMobile} />}
    </Media>
));
