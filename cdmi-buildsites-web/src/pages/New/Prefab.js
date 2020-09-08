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
    Alert,
} from 'antd';
import DescriptionList from '@/components/DescriptionList';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './Prefab.less';

const FormItem = Form.Item;
const { Step } = Steps;
const { TextArea } = Input;
const { Option } = Select;
const { MonthPicker, RangePicker } = DatePicker;
const { Description } = DescriptionList;
const RadioGroup = Radio.Group;
const TabPane = Tabs.TabPane;

const getValue = obj =>
    Object.keys(obj)
        .map(key => obj[key])
        .join(',');


@Form.create()
class BindPrefabForm extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            current: 0,
        };
    };

    handleNext = currentStep => {
        const { form, handleUpdate } = this.props;
        const { formVals: oldValue } = this.state;
        form.validateFields((err, fieldsValue) => {
            if (err) return;
            const formVals = { ...oldValue, ...fieldsValue };
            this.setState(
                {
                    formVals,
                },
                () => {
                    if (currentStep < 2) {
                        this.forward();
                    } else {
                        handleUpdate(formVals);
                    }
                }
            );
        });
    };

    backward = () => {
        const { currentStep } = this.state;
        this.setState({
            currentStep: currentStep - 1,
        });
    };

    forward = () => {
        const { currentStep } = this.state;
        this.setState({
            currentStep: currentStep + 1,
        });
    };
    render() {
        const { current } = this.state;
        const {
            form,
            handleDrawerVisible,
            data,
            isMobile,
        } = this.props;
        const { drawerVisible } = this.state;
        const width = isMobile ? `100%` : 720;
        return (
            <Drawer
                title="预制构件登记入库"
                width={width}
                onClose={() => handleDrawerVisible()}
                visible={drawerVisible}
                style={{
                    overflow: 'auto',
                    height: 'calc(100% - 108px)',
                    paddingBottom: '108px',
                }}
            >
                <Card bordered={false} bodyStyle={{ padding: 0 }}>
                    <DescriptionList size="large" title="任务详情" style={{ marginBottom: 32 }} col={2}>
                        <Description term="预制件名称">桥梁320标段75-403</Description>
                        <Description term="负责班组">李坤工程组</Description>
                    </DescriptionList>
                    <Divider style={{ marginBottom: 32 }} />
                    <Steps direction="horizontal" current={current}>
                        <Step title="构件基本信息" />
                        <Step title="登记构件原料" />
                        <Step title="登记下一个" />
                    </Steps>
                </Card>
            </Drawer>
        );
    }
}

// @Form.create()
// class StepOne extends PureComponent {
//     render() {
//         const {
//             isMobile,
//         } = this.props;
//         const width = isMobile ? `100%` : 720;
//         return (
//             <Fragment>
//                 <div className={styles.title}>预制件基本信息</div>
//                 <Form layout="vertical">
//                     <Row gutter={16}>
//                         <Col span={12}>
//                             <Form.Item label="构件编号">
//                                 {form.getFieldDecorator('group', {
//                                     rules: [{ required: true, message: '必须选择质检结果' }],
//                                 })(
//                                     <Input placeholder="输入构件编号" />
//                                 )}
//                             </Form.Item>
//                         </Col>
//                         <Col span={12}>
//                             <Form.Item label="使用年限">
//                                 {form.getFieldDecorator('number')(<Input placeholder="输入使用年限" />)}
//                             </Form.Item>
//                         </Col>
//                     </Row>
//                     <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
//                         <Col sm={8} md={24}>
//                             <Form.Item label="安装位置">
//                                 {form.getFieldDecorator('type')(<Input placeholder="请输入安装位置" />)}
//                             </Form.Item>
//                         </Col>
//                     </Row>
//                     <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
//                         <Col sm={8} md={24}>
//                             <Form.Item>
//                                 <label style={{ marginRight: 6, color: '#f5222d' }}>*</label><Checkbox><label style={{ color: 'rgba(0, 0, 0, 0.85)' }}>质检质量合格</label></Checkbox>
//                             </Form.Item>
//                         </Col>
//                     </Row>
//                     <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
//                         <Col sm={8} md={24}>
//                             <Form.Item label="上传质检报告">
//                                 {/* {form.getFieldDecorator('approver', {
//                                 rules: [{ required: true, message: 'Please enter user name' }],
//                             })(<Input placeholder="上传照片" />)} */}
//                                 <PicturesWall />
//                             </Form.Item>
//                         </Col>
//                     </Row>
//                 </Form>
//                 <div
//                     style={{
//                         position: 'absolute',
//                         left: 0,
//                         bottom: 0,
//                         width: '100%',
//                         borderTop: '1px solid #e9e9e9',
//                         padding: '10px 16px',
//                         background: '#fff',
//                         textAlign: 'right',
//                     }}
//                 >
//                     <Button onClick={() => handleDrawerVisible()} style={{ marginRight: 8 }}>
//                         取消
//             </Button>
//                     <Button onClick={okHandle} type="primary">
//                         登记下一个
//             </Button>
//                 </div>
//             </Fragment>
//         );
//     }
// }


/* eslint react/no-multi-comp:0 */
@connect(({ rule, loading }) => ({
    rule,
    trains: rule.data,
    loading: loading.models.rule,
}))
@Form.create()
class Prefab extends PureComponent {
    constructor(props) {
        super(props);
    };

    state = {
        drawerVisible: false,
        updateModalVisible: false,
        expandForm: false,
        selectedRows: [],
        formValues: {},
        stepFormValues: {},
    };

    columns = !this.props.isMobile ? [
        {
            title: '构件编号',
            dataIndex: 'name',
        },
        {
            title: '构件名称',
            dataIndex: 'idcard',
        },
        {
            title: '里程桩号',
            dataIndex: 'phonenumber',
        },
        {
            title: '安装位置',
            dataIndex: 'group',
        },
        {
            title: '质检报告',
            dataIndex: 'report',
        },
        {
            title: '操作',
            render: (text, record) => (
                <Fragment>
                    <a onClick={() => this.handleDrawerVisible(true, record)}>详细</a>
                </Fragment>
            ),
        },
    ] : [
            {
                title: '材料名称',
                dataIndex: 'name',
            },
            {
                title: '操作',
                render: (text, record) => (
                    <Fragment>
                        <a onClick={() => this.handleDrawerVisible(true, record)}>详细</a>
                    </Fragment>
                ),
            },
        ];

    task_columns = !this.props.isMobile ? [
        {
            title: '创建时间',
            dataIndex: 'name',
        },
        {
            title: '构件名称',
            dataIndex: 'idcard',
        },
        {
            title: '负责班组',
            dataIndex: 'phonenumber',
        },
        {
            title: '未登记数量',
            dataIndex: 'group',
        },
        {
            title: '操作',
            render: (text, record) => (
                <Fragment>
                    <a onClick={() => this.handleDrawerVisible(true, record)}>登记</a>
                </Fragment>
            ),
        },
    ] : [
            {
                title: '构件名称',
                dataIndex: 'name',
            },
            {
                title: '操作',
                render: (text, record) => (
                    <Fragment>
                        <a onClick={() => this.handleDrawerVisible(true, record)}>登记</a>
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

    toggleForm = () => {
        const { expandForm } = this.state;
        this.setState({
            expandForm: !expandForm,
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

    handleDrawerVisible = (flag, record) => {
        this.setState({
            drawerVisible: !!flag,
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
        this.handleDrawerVisible();
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

    renderForm() {
        const { expandForm } = this.state;
        return expandForm ? this.renderAdvancedForm() : this.renderSimpleForm();
    };
    renderSimpleForm() {
        const {
            form: { getFieldDecorator },
        } = this.props;
        return (
            <Form onSubmit={this.handleSearch} layout="inline">
                <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                    <Col md={8} sm={24}>
                        <FormItem label="构件编号">
                            {getFieldDecorator('id')(<Input placeholder="请输入" />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="构件名称">
                            {getFieldDecorator('name')(<Input placeholder="请输入" />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <span className={styles.submitButtons}>
                            <Button type="primary" htmlType="submit">
                                查询
              </Button>
                            <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
                                重置
              </Button>
                            <a style={{ marginLeft: 8 }} onClick={this.toggleForm}>
                                展开 <Icon type="down" />
                            </a>
                        </span>
                    </Col>
                </Row>
            </Form>
        );
    }

    renderAdvancedForm() {
        const {
            form: { getFieldDecorator },
        } = this.props;
        return (
            <Form onSubmit={this.handleSearch} layout="inline">
                <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                    <Col md={8} sm={24}>
                        <FormItem label="构件编号">
                            {getFieldDecorator('id')(<Input placeholder="请输入" />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="构件名称">
                            {getFieldDecorator('name')(<Input placeholder="请输入" />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="责任班组">
                            {getFieldDecorator('group')(
                                <Select placeholder="请选择" style={{ width: '100%' }}>
                                    <Option value="0">离场</Option>
                                    <Option value="1">已入场</Option>
                                </Select>
                            )}
                        </FormItem>
                    </Col>
                </Row>
                <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                    <Col md={8} sm={24}>
                        <FormItem label="里程桩号">
                            {getFieldDecorator('number')(<Input style={{ width: '100%' }} />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="安装位置">
                            {getFieldDecorator('phone')(<InputNumber style={{ width: '100%' }} />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="质检状态">
                            {getFieldDecorator('status')(
                                <Select placeholder="请选择" style={{ width: '100%' }}>
                                    <Option value="0">未质检</Option>
                                    <Option value="1">质检合格</Option>
                                    <Option value="2">质检不合格</Option>
                                </Select>
                            )}
                        </FormItem>
                    </Col>
                </Row>
                <div style={{ overflow: 'hidden' }}>
                    <div style={{ float: 'right', marginBottom: 24 }}>
                        <Button type="primary" htmlType="submit">
                            查询
            </Button>
                        <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
                            重置
            </Button>
                        <a style={{ marginLeft: 8 }} onClick={this.toggleForm}>
                            收起 <Icon type="up" />
                        </a>
                    </div>
                </div>
            </Form>
        );
    }

    render() {
        const {
            rule: { data },
            loading,
            isMobile,
        } = this.props;
        const { drawerVisible, updateModalVisible, stepFormValues, pagination } = this.state;
        // const menu = (
        //     <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        //         <Menu.Item key="remove">删除</Menu.Item>
        //         <Menu.Item key="approval">批量审批</Menu.Item>
        //     </Menu>
        // );
        const parentMethods = {
            handleAdd: this.handleAdd,
            handleDrawerVisible: this.handleDrawerVisible,
        };
        const updateMethods = {
            handleUpdateModalVisible: this.handleUpdateModalVisible,
            handleUpdate: this.handleUpdate,
        };
        const paginationProps = {
            showSizeChanger: true,
            showQuickJumper: true,
            ...pagination,
        };
        return (
            <PageHeaderWrapper>
                <Card bordered={false}>
                    <Tabs type="card" tabBarGutter={8} defaultActiveKey="1">
                        <TabPane tab={<span><Icon type="apple" />预制件库</span>} key="1" >
                            <div className={styles.tableList} >
                                <div className={styles.tableListForm}>{this.renderForm()}</div>
                            </div>
                            <div style={{ paddingBottom: 6 }}></div>
                            <Table
                                loading={loading}
                                dataSource={data.list}
                                columns={this.columns}
                                pagination={paginationProps}
                                onSelectRow={this.handleSelectRows}
                                onChange={this.handleStandardTableChange}
                            />
                        </TabPane>
                        <TabPane tab={<span><Icon type="plus" />登记入库</span>} key="2" >
                            <div className={styles.standardTable}>
                                <div className={styles.tableAlert}>
                                    <Alert
                                        message={
                                            <Fragment>
                                                从生产任务中选择已生产好的预制件进行登记
                                            </Fragment>
                                        }
                                        type="info"
                                        showIcon
                                    />
                                </div>
                                <Table
                                    loading={loading}
                                    dataSource={data.list}
                                    columns={this.task_columns}
                                    pagination={paginationProps}
                                    onSelectRow={this.handleSelectRows}
                                    onChange={this.handleStandardTableChange}
                                />
                            </div>
                        </TabPane>
                    </Tabs>
                </Card>
                {stepFormValues && Object.keys(stepFormValues).length ? (
                    <BindPrefabForm
                        {...updateMethods}
                        drawerVisible={drawerVisible}
                        values={stepFormValues}
                        isMobile={isMobile}
                    />
                ) : null}
            </PageHeaderWrapper>
        );
    }
}

// export default Worker;
export default connect()(props => (
    <Media query="(max-width: 599px)">
        {isMobile => <Prefab {...props} isMobile={isMobile} />}
    </Media>
));
