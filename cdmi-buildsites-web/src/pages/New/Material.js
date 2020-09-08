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
} from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './TableList.less';

const FormItem = Form.Item;
const { Step } = Steps;
const { TextArea } = Input;
const { Option } = Select;
const { MonthPicker, RangePicker } = DatePicker;
const RadioGroup = Radio.Group;
const TabPane = Tabs.TabPane;

const getValue = obj =>
    Object.keys(obj)
        .map(key => obj[key])
        .join(',');
const statusMap = ['default', 'processing', 'success', 'error'];
const status = ['草稿', '已分配班组', '进行中', '已完成'];

const CreateForm = Form.create()(props => {
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
            title="新增原料入库"
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
                        <Form.Item label="原料类型">
                            {form.getFieldDecorator('type', {
                                rules: [{ required: true, message: '任务名称为必须填写项' }],
                            })(<Select placeholder="选择类型">
                                <Option value="private">水泥</Option>
                                <Option value="public">钢材</Option>
                            </Select>)}
                        </Form.Item>
                    </Col>
                    <Col sm={8} md={24}>
                        <Form.Item label="原料名称">
                            {form.getFieldDecorator('name', {
                                rules: [{ required: true, message: '任务名称为必须填写项' }],
                            })(<Input placeholder="输入任务名称" />)}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                    <Col sm={8} md={24}>
                        <Form.Item label="入场时间">
                            {form.getFieldDecorator('type', {
                                rules: [{ required: true, message: '任务名称为必须填写项' }],
                            })(<DatePicker style={{ width: '100%' }} placeholder="请输入更新日期" />)}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item label="厂商">
                            {form.getFieldDecorator('group')(
                                <Input placeholder="输入原料型号规格" />
                            )}
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label="型号规格">
                            {form.getFieldDecorator('number')(<Input placeholder="输入原料型号规格" />)}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item label="批号">
                            {form.getFieldDecorator('group')(
                                <Input placeholder="输入原料型号规格" />
                            )}
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label="数量">
                            {form.getFieldDecorator('number')(<Input placeholder="输入原料型号规格" />)}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                    <Col sm={8} md={24}>
                        <Form.Item label="上传出厂质检报告">
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
class Material extends PureComponent {
    constructor(props) {
        super(props);
    };

    state = {
        modalVisible: false,
        updateModalVisible: false,
        selectedRows: [],
        formValues: {},
        stepFormValues: {},
    };

    columns = !this.props.isMobile ? [
        {
            title: '入库时间',
            dataIndex: 'name',
        },
        {
            title: '材料名称',
            dataIndex: 'idcard',
        },
        {
            title: '材料类型',
            dataIndex: 'phonenumber',
            filters: [
                {
                    text: status[0],
                    value: 0,
                },
                {
                    text: status[1],
                    value: 1,
                },
                {
                    text: status[2],
                    value: 2,
                },
                {
                    text: status[3],
                    value: 3,
                },
            ],
            render(val) {
                return <Badge status={statusMap[val]} text={status[val]} />;
            },
        },
        {
            title: '出厂报告',
            dataIndex: 'group',
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
                title: '材料名称',
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

    renderSearchForm() {
        const {
            form: { getFieldDecorator },
        } = this.props;
        return (
            <Form onSubmit={this.handleSearch} layout="inline">
                <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                    <Col md={8} sm={24}>
                        <FormItem label="材料名称">
                            {getFieldDecorator('name')(<Input placeholder="请输入" />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="材料类型">
                            {getFieldDecorator('status')(
                                <Select placeholder="请选择" style={{ width: '100%' }}>
                                    <Option value="0">钢材</Option>
                                    <Option value="1">水泥</Option>
                                    <Option value="2">添加剂</Option>
                                </Select>
                            )}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="入库时间">
                            {getFieldDecorator('joinDate')(
                                <RangePicker style={{ width: '100%' }} />
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
        const { selectedRows, modalVisible, updateModalVisible, stepFormValues } = this.state;
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
        return (
            <PageHeaderWrapper>
                <Card bordered={false}>
                    <div className={styles.tableList}>
                        <div className={styles.tableListForm}>{this.renderSearchForm()}</div>
                        <div className={styles.tableListOperator}>
                            <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true)}>
                                新入库
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
                </Card>
                <CreateForm {...parentMethods} modalVisible={modalVisible} isMobile={isMobile} />
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
            </PageHeaderWrapper>
        );
    }
}

// export default Worker;
export default connect()(props => (
    <Media query="(max-width: 599px)">
        {isMobile => <Material {...props} isMobile={isMobile} />}
    </Media>
));
