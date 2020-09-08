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
} from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './TableList.less';

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
const status = ['已实名', '已培训', '已进场', '有欠薪'];

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
            title="新增工人信息"
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
                <Card
                    bordered={false}
                >
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="姓名">
                                {form.getFieldDecorator('name', {
                                    rules: [{ required: true, message: '请输入工人名称' }],
                                })(<Input placeholder="可先上传身份证自动识别填充" />)}
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="性别">
                                {form.getFieldDecorator('sex', {
                                    rules: [{ required: true, message: '请选择性别' }],
                                })(
                                    <Select placeholder="请选择性别">
                                        <Option value="male">男</Option>
                                        <Option value="female">女</Option>
                                    </Select>
                                )}
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="年龄">
                                {form.getFieldDecorator('old', {
                                    rules: [{ required: true, message: '请填写年龄' }],
                                })(
                                    <InputNumber min={18} max={80} placeholder="可先上传身份证自动识别填充" style={{ width: '100%' }} />
                                )}
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="血型">
                                {form.getFieldDecorator('blood', {
                                    rules: [{ required: true, message: '请选择血型' }],
                                })(
                                    <Select placeholder="请选择血型" onChange={handleBloodChange} suffixIcon={<Icon type="plus" />}>
                                        <Option value="A血型">A血型</Option>
                                        <Option value="B血型">B血型</Option>
                                        <Option value="O血型">O血型</Option>
                                        <Option value="AB血型">AB血型</Option>
                                        <Option value="RH血型">RH血型</Option>
                                    </Select>
                                )}
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                        <Col sm={8} md={12}>
                            <Form.Item label="上传工人大头照">
                                {/* {form.getFieldDecorator('approver', {
                                rules: [{ required: true, message: 'Please enter user name' }],
                            })(<Input placeholder="上传照片" />)} */}
                                <IdCardPicturesWall name="avatar" title="工人大头照" />
                            </Form.Item>
                        </Col>
                        <Col sm={8} md={12}>
                            <Form.Item label="上传工人身份证正反复印件">
                                <div style={{ display: 'flex' }}>
                                    <IdCardPicturesWall name="idface" title="身份证正面照" />
                                    <IdCardPicturesWall name="idback" title="身份证背面照" />
                                </div>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                        <Col span={24}>
                            <Form.Item label="上传体检报告">
                                {/* {form.getFieldDecorator('approver', {
                                rules: [{ required: true, message: 'Please enter user name' }],
                            })(<Input placeholder="上传照片" />)} */}
                                <IdCardPicturesWall name="report" title="体检报告" />
                            </Form.Item>
                        </Col>
                    </Row>
                </Card>
                <Card
                    title="在场信息"
                    bordered={false}
                >
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="所属班组">
                                {form.getFieldDecorator('group', {
                                    rules: [{ required: true, message: 'Please choose the type' }],
                                })(
                                    <Select placeholder="Please choose the type">
                                        <Option value="private">Private</Option>
                                        <Option value="public">Public</Option>
                                    </Select>
                                )}
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="入场时间">
                                {form.getFieldDecorator('date')(
                                    <DatePicker style={{ width: '100%' }} placeholder="请输入更新日期" />
                                )}
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="工种">
                                {form.getFieldDecorator('type', {
                                    rules: [{ required: true, message: 'Please choose the type' }],
                                })(
                                    <Select placeholder="Please choose the type">
                                        <Option value="private">水电工</Option>
                                        <Option value="public">木工</Option>
                                    </Select>
                                )}
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="工资">
                                {form.getFieldDecorator('url', {
                                    rules: [{ required: true, message: 'Please enter url' }],
                                })(
                                    <Input
                                        style={{ width: '100%' }}
                                        addonAfter="每月"
                                        placeholder="请输入工资"
                                    />
                                )}
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={24}>
                            <Checkbox>已离场</Checkbox>
                        </Col>
                    </Row>
                    <Row gutter={16} style={{ marginTop: 20 }}>
                        <Col span={12}>
                            <Form.Item label="离场时间">
                                {form.getFieldDecorator('date')(
                                    <DatePicker style={{ width: '100%' }} placeholder="请输入更新日期" />
                                )}
                            </Form.Item>
                        </Col>
                    </Row>
                </Card>
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

function getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
}

@Form.create()
class UpdateForm extends PureComponent {
    static defaultProps = {
        handleUpdate: () => { },
        handleUpdateModalVisible: () => { },
        values: {},
    };

    constructor(props) {
        super(props);

        this.state = {
            formVals: {
                name: props.values.name,
                desc: props.values.desc,
                key: props.values.key,
                target: '0',
                template: '0',
                type: '1',
                time: '',
                frequency: 'month',
            },
            selectedRows: [],
            currentStep: 0,
        };

        this.formLayout = {
            labelCol: { span: 7 },
            wrapperCol: { span: 13 },
        };
    }

    columns = this.props.isMobile ? [
        {
            title: '培训时间',
            dataIndex: 'trainDate',
            sorter: true,
            render: val => <span>{moment(val).format('YYYY-MM-DD')}</span>,
        },
        {
            title: '培训内容',
            dataIndex: 'idcard',
        },
    ] : [
            {
                title: '培训时间',
                dataIndex: 'trainDate',
                sorter: true,
                render: val => <span>{moment(val).format('YYYY-MM-DD')}</span>,
            },
            {
                title: '培训内容',
                dataIndex: 'title',
            },
            {
                title: '培训次数',
                dataIndex: 'times',
            },
            {
                title: '课程类型',
                dataIndex: 'status',
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
        ];

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

    renderContent = (currentStep, formVals) => {
        const { form } = this.props;
        if (currentStep === 1) {
            return [
                <FormItem key="target" {...this.formLayout} label="监控对象">
                    {form.getFieldDecorator('target', {
                        initialValue: formVals.target,
                    })(
                        <Select style={{ width: '100%' }}>
                            <Option value="0">表一</Option>
                            <Option value="1">表二</Option>
                        </Select>
                    )}
                </FormItem>,
                <FormItem key="template" {...this.formLayout} label="规则模板">
                    {form.getFieldDecorator('template', {
                        initialValue: formVals.template,
                    })(
                        <Select style={{ width: '100%' }}>
                            <Option value="0">规则模板一</Option>
                            <Option value="1">规则模板二</Option>
                        </Select>
                    )}
                </FormItem>,
                <FormItem key="type" {...this.formLayout} label="规则类型">
                    {form.getFieldDecorator('type', {
                        initialValue: formVals.type,
                    })(
                        <RadioGroup>
                            <Radio value="0">强</Radio>
                            <Radio value="1">弱</Radio>
                        </RadioGroup>
                    )}
                </FormItem>,
            ];
        }
        if (currentStep === 2) {
            return [
                <FormItem key="time" {...this.formLayout} label="开始时间">
                    {form.getFieldDecorator('time', {
                        rules: [{ required: true, message: '请选择开始时间！' }],
                    })(
                        <DatePicker
                            style={{ width: '100%' }}
                            showTime
                            format="YYYY-MM-DD HH:mm:ss"
                            placeholder="选择开始时间"
                        />
                    )}
                </FormItem>,
                <FormItem key="frequency" {...this.formLayout} label="调度周期">
                    {form.getFieldDecorator('frequency', {
                        initialValue: formVals.frequency,
                    })(
                        <Select style={{ width: '100%' }}>
                            <Option value="month">月</Option>
                            <Option value="week">周</Option>
                        </Select>
                    )}
                </FormItem>,
            ];
        }
        return [
            <FormItem key="name" {...this.formLayout} label="规则名称">
                {form.getFieldDecorator('name', {
                    rules: [{ required: true, message: '请输入规则名称！' }],
                    initialValue: formVals.name,
                })(<Input placeholder="请输入" />)}
            </FormItem>,
            <FormItem key="desc" {...this.formLayout} label="规则描述">
                {form.getFieldDecorator('desc', {
                    rules: [{ required: true, message: '请输入至少五个字符的规则描述！', min: 5 }],
                    initialValue: formVals.desc,
                })(<TextArea rows={4} placeholder="请输入至少五个字符" />)}
            </FormItem>,
        ];
    };

    renderFooter = currentStep => {
        const { handleUpdateModalVisible, values } = this.props;
        if (currentStep === 1) {
            return [
                <Button key="back" style={{ float: 'left' }} onClick={this.backward}>
                    上一步
        </Button>,
                <Button key="cancel" onClick={() => handleUpdateModalVisible(false, values)}>
                    取消
        </Button>,
                <Button key="forward" type="primary" onClick={() => this.handleNext(currentStep)}>
                    下一步
        </Button>,
            ];
        }
        if (currentStep === 2) {
            return [
                <Button key="back" style={{ float: 'left' }} onClick={this.backward}>
                    上一步
        </Button>,
                <Button key="cancel" onClick={() => handleUpdateModalVisible(false, values)}>
                    取消
        </Button>,
                <Button key="submit" type="primary" onClick={() => this.handleNext(currentStep)}>
                    完成
        </Button>,
            ];
        }
        return [
            <Button key="cancel" onClick={() => handleUpdateModalVisible(false, values)}>
                取消
      </Button>,
            <Button key="forward" type="primary" onClick={() => this.handleNext(currentStep)}>
                下一步
      </Button>,
        ];
    };

    render() {
        const { trains, loading, form, updateModalVisible, handleUpdateModalVisible, values, isMobile } = this.props;
        const { selectedRows, currentStep, formVals } = this.state;
        const width = isMobile ? `100%` : 720;
        return (
            // <Modal
            //     width={640}
            //     bodyStyle={{ padding: '32px 40px 48px' }}
            //     destroyOnClose
            //     title="规则配置"
            //     visible={updateModalVisible}
            //     footer={this.renderFooter(currentStep)}
            //     onCancel={() => handleUpdateModalVisible(false, values)}
            //     afterClose={() => handleUpdateModalVisible()}
            // >
            //     <Steps style={{ marginBottom: 28 }} size="small" current={currentStep}>
            //         <Step title="基本信息" />
            //         <Step title="配置规则属性" />
            //         <Step title="设定调度周期" />
            //     </Steps>
            //     {this.renderContent(currentStep, formVals)}
            // </Modal>
            <Drawer
                title="查看工人信息"
                width={width}
                onClose={() => handleUpdateModalVisible(false, values)}
                visible={updateModalVisible}
                style={{
                    overflow: 'auto',
                    height: 'calc(100% - 108px)',
                    paddingBottom: '108px',
                }}
            >
                <Tabs defaultActiveKey="1">
                    <TabPane tab={<span><Icon type="apple" />基本信息</span>} key="1">
                        <Form layout="vertical" hideRequiredMark>
                            <Card
                                bordered={false}
                            >
                                <Row gutter={16}>
                                    <Col span={12}>
                                        <Form.Item label="姓名">
                                            {form.getFieldDecorator('name', {
                                                rules: [{ required: true, message: '请输入工人名称' }],
                                            })(<Input placeholder="可先上传身份证自动识别填充" />)}
                                        </Form.Item>
                                    </Col>
                                    <Col span={12}>
                                        <Form.Item label="性别">
                                            {form.getFieldDecorator('sex', {
                                                rules: [{ required: true, message: '请选择性别' }],
                                            })(
                                                <Select placeholder="请选择性别">
                                                    <Option value="male">男</Option>
                                                    <Option value="female">女</Option>
                                                </Select>
                                            )}
                                        </Form.Item>
                                    </Col>
                                </Row>
                                <Row gutter={16}>
                                    <Col span={12}>
                                        <Form.Item label="年龄">
                                            {form.getFieldDecorator('old', {
                                                rules: [{ required: true, message: '请填写年龄' }],
                                            })(
                                                <InputNumber min={18} max={80} placeholder="可先上传身份证自动识别填充" style={{ width: '100%' }} />
                                            )}
                                        </Form.Item>
                                    </Col>
                                    <Col span={12}>
                                        <Form.Item label="血型">
                                            {form.getFieldDecorator('blood', {
                                                rules: [{ required: true, message: '请选择血型' }],
                                            })(
                                                <Select placeholder="请选择血型" suffixIcon={<Icon type="plus" />}>
                                                    <Option value="A血型">A血型</Option>
                                                    <Option value="B血型">B血型</Option>
                                                    <Option value="O血型">O血型</Option>
                                                    <Option value="AB血型">AB血型</Option>
                                                    <Option value="RH血型">RH血型</Option>
                                                </Select>
                                            )}
                                        </Form.Item>
                                    </Col>
                                </Row>
                                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                                    <Col sm={8} md={12}>
                                        <Form.Item label="上传工人大头照">
                                            {/* {form.getFieldDecorator('approver', {
                                rules: [{ required: true, message: 'Please enter user name' }],
                            })(<Input placeholder="上传照片" />)} */}
                                            <IdCardPicturesWall name="avatar" title="工人大头照" />
                                        </Form.Item>
                                    </Col>
                                    <Col sm={8} md={12}>
                                        <Form.Item label="上传工人身份证正反复印件">
                                            <div style={{ display: 'flex' }}>
                                                <IdCardPicturesWall name="idface" title="身份证正面照" />
                                                <IdCardPicturesWall name="idback" title="身份证背面照" />
                                            </div>
                                        </Form.Item>
                                    </Col>
                                </Row>
                                <Row gutter={{ xs: 8, sm: 16, md: 24 }}>
                                    <Col span={24}>
                                        <Form.Item label="上传体检报告">
                                            {/* {form.getFieldDecorator('approver', {
                                rules: [{ required: true, message: 'Please enter user name' }],
                            })(<Input placeholder="上传照片" />)} */}
                                            <IdCardPicturesWall name="report" title="体检报告" />
                                        </Form.Item>
                                    </Col>
                                </Row>
                            </Card>
                        </Form>
                    </TabPane>
                    <TabPane tab={<span><Icon type="android" />在场信息</span>} key="2">
                        <Form layout="vertical" hideRequiredMark>
                            <Card
                                bordered={false}
                            >
                                <Row gutter={16}>
                                    <Col span={12}>
                                        <Form.Item label="所属班组">
                                            {form.getFieldDecorator('group', {
                                                rules: [{ required: true, message: 'Please choose the type' }],
                                            })(
                                                <Select placeholder="Please choose the type">
                                                    <Option value="private">Private</Option>
                                                    <Option value="public">Public</Option>
                                                </Select>
                                            )}
                                        </Form.Item>
                                    </Col>
                                    <Col span={12}>
                                        <Form.Item label="入场时间">
                                            {form.getFieldDecorator('date')(
                                                <DatePicker style={{ width: '100%' }} placeholder="请输入更新日期" />
                                            )}
                                        </Form.Item>
                                    </Col>
                                </Row>
                                <Row gutter={16}>
                                    <Col span={12}>
                                        <Form.Item label="工种">
                                            {form.getFieldDecorator('type', {
                                                rules: [{ required: true, message: 'Please choose the type' }],
                                            })(
                                                <Select placeholder="Please choose the type">
                                                    <Option value="private">水电工</Option>
                                                    <Option value="public">木工</Option>
                                                </Select>
                                            )}
                                        </Form.Item>
                                    </Col>
                                    <Col span={12}>
                                        <Form.Item label="工资">
                                            {form.getFieldDecorator('url', {
                                                rules: [{ required: true, message: 'Please enter url' }],
                                            })(
                                                <Input
                                                    style={{ width: '100%' }}
                                                    addonAfter="每月"
                                                    placeholder="请输入工资"
                                                />
                                            )}
                                        </Form.Item>
                                    </Col>
                                </Row>
                                <Row gutter={16}>
                                    <Col span={24}>
                                        <Checkbox>已离场</Checkbox>
                                    </Col>
                                </Row>
                                <Row gutter={16} style={{ marginTop: 20 }}>
                                    <Col span={12}>
                                        <Form.Item label="离场时间">
                                            {form.getFieldDecorator('date')(
                                                <DatePicker style={{ width: '100%' }} placeholder="请输入更新日期" />
                                            )}
                                        </Form.Item>
                                    </Col>
                                </Row>
                            </Card>
                        </Form>
                    </TabPane>
                    <TabPane tab={<span><Icon type="android" />培训记录</span>} key="3">
                        <StandardTable
                            selectedRows={selectedRows}
                            loading={loading}
                            data={trains}
                            columns={this.columns}
                        />
                    </TabPane>
                </Tabs>
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
                    <Button onClick={() => handleUpdateModalVisible(false, values)} style={{ marginRight: 8 }}>
                        取消
            </Button>
                    <Button type="primary">
                        编辑
            </Button>
                </div>
            </Drawer>
        );
    }
}

/** 创建上传身份证照片墙 */
@Form.create()
class IdCardPicturesWall extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            previewVisible: false,
            previewImage: '',
            facefile: null,
            backfile: null,
            loading: false,
            name: props.name,
            title: props.title,
        };
    }

    handleCancel = () => this.setState({ previewVisible: false })

    handlePreview = (file) => {
        this.setState({
            previewImage: file.url || file.thumbUrl,
            previewVisible: true,
        });
    }
    handleChange = (info) => {
        if (info.file.status === 'uploading') {
            this.setState({ loading: true });
            return;
        }
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, imageUrl => this.setState({
                imageUrl,
                loading: false,
            }));
        }
    }

    render() {
        const { previewVisible, previewImage, name, title, facefile, backfile } = this.state;
        const imageUrl = this.state.imageUrl;

        const uploadButton = (
            <div>
                <Icon type={this.state.loading ? 'loading' : 'plus'} />
                <div className="ant-upload-text">{title}</div>
            </div>
        );

        return (
            <Upload
                name={name}
                listType="picture-card"
                className="avatar-uploader"
                showUploadList={false}
                action="//jsonplaceholder.typicode.com/posts/"
                // beforeUpload={beforeUpload}
                onChange={this.handleChange}
            >
                {imageUrl ? <img src={imageUrl} alt="avatar" style={{ width: 128, height: 128 }} /> : uploadButton}
            </Upload>
        );
    }
}


/** 创建新增工人信息的抽屉控件 */
@Form.create()
class NewWorkerDrawerForm extends React.Component {
    state = { visible: false };
    showDrawer = () => {
        this.setState({
            visible: true,
        });
    };

    onClose = () => {
        this.setState({
            visible: false,
        });
    };

    render() {
        const { getFieldDecorator } = this.props.form;
        return (
            <div>
                <Button type="primary" onClick={this.showDrawer}>
                    <Icon type="plus" /> New account
                </Button>

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
class Worker extends PureComponent {
    constructor(props) {
        super(props);
    };

    state = {
        modalVisible: false,
        updateModalVisible: false,
        expandForm: false,
        selectedRows: [],
        formValues: {},
        stepFormValues: {},
    };

    columns = !this.props.isMobile ? [
        {
            title: '姓名',
            dataIndex: 'name',
        },
        {
            title: '身份证号',
            dataIndex: 'idcard',
        },
        {
            title: '联系电话',
            dataIndex: 'phonenumber',
        },
        {
            title: '所在班组',
            dataIndex: 'group',
        },
        {
            title: '状态',
            dataIndex: 'status',
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
            title: '入场时间',
            dataIndex: 'updatedAt',
            sorter: true,
            render: val => <span>{moment(val).format('YYYY-MM-DD')}</span>,
        },
        {
            title: '操作',
            render: (text, record) => (
                <Fragment>
                    <a onClick={() => this.handleUpdateModalVisible(true, record)}>详细</a>
                </Fragment>
            ),
        },
    ] : [
            {
                title: '姓名',
                dataIndex: 'name',
            },
            {
                title: '操作',
                render: (text, record) => (
                    <Fragment>
                        <a onClick={() => this.handleUpdateModalVisible(true, record)}>详细</a>
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

    handleModalVisible = flag => {
        this.setState({
            modalVisible: !!flag,
        });
    };

    handleUpdateModalVisible = (flag, record) => {
        this.setState({
            updateModalVisible: !!flag,
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

    renderSimpleForm() {
        const {
            form: { getFieldDecorator },
        } = this.props;
        return (
            <Form onSubmit={this.handleSearch} layout="inline">
                <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                    <Col md={8} sm={24}>
                        <FormItem label="工人姓名">
                            {getFieldDecorator('name')(<Input placeholder="请输入" />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="在场状态">
                            {getFieldDecorator('status')(
                                <Select placeholder="请选择" style={{ width: '100%' }}>
                                    <Option value="0">离场</Option>
                                    <Option value="1">已入场</Option>
                                </Select>
                            )}
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
                        <FormItem label="工人姓名">
                            {getFieldDecorator('name')(<Input placeholder="请输入" />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="在场状态">
                            {getFieldDecorator('status')(
                                <Select placeholder="请选择" style={{ width: '100%' }}>
                                    <Option value="0">离场</Option>
                                    <Option value="1">已入场</Option>
                                </Select>
                            )}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="所在班组">
                            {getFieldDecorator('status')(
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
                        <FormItem label="身份证号">
                            {getFieldDecorator('number')(<Input style={{ width: '100%' }} />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="手机号码">
                            {getFieldDecorator('phone')(<InputNumber style={{ width: '100%' }} />)}
                        </FormItem>
                    </Col>
                    <Col md={8} sm={24}>
                        <FormItem label="入场日期">
                            {getFieldDecorator('joinDate')(
                                <DatePicker style={{ width: '100%' }} placeholder="请输入更新日期" />
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

    renderForm() {
        const { expandForm } = this.state;
        return expandForm ? this.renderAdvancedForm() : this.renderSimpleForm();
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
                        <div className={styles.tableListForm}>{this.renderForm()}</div>
                        <div className={styles.tableListOperator}>
                            <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true)}>
                                新建
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
                    <UpdateForm
                        {...updateMethods}
                        updateModalVisible={updateModalVisible}
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
        {isMobile => <Worker {...props} isMobile={isMobile} />}
    </Media>
));
