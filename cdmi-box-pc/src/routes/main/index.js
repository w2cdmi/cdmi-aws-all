import React from 'react'
import {Link} from 'react-router-dom'
import {
  Layout,
  Menu,
  Icon,
  Progress,
  Badge,
  Row,
  Col
} from 'antd'
import { Component } from 'wolf'
import { DynamicSubRoute } from 'utils'
import { Modal, Button } from 'antd';
import { Collapse } from 'antd';
import { getUserInfo } from 'utils'
import styles from './index.less'
// import Websocket from 'react-websocket';

const {Header, Sider, Content} = Layout
const Panel = Collapse.Panel;

export default class MainFrames extends Component {
  constructor(props) {
    super(props);
    this.state = {
      count: 90,
      visible: false,
    dataSource: {},
    mapdata: '123',
    messageUrl: '',
    keys: '1'
    };
  }
  componentDidMount () {
    this.cloudUserId = getUserInfo().cloudUserId
    this.ds = this.http()
    this.getData(0);
    this.getMessageData();
  }
  getData = (id, sortid, callback) => {
    this.ds.url = `/folders/${this.cloudUserId}/${id}/items`
    this.ds.data = {}
    if (sortid) {
      this.ds.data = JSON.stringify({ order: [{ field: sortid, direction: 'DESC' }] })
    }
    this.ds.post((data) => {
      if (callback) {
        callback()
      }
      this.setState({
        dataSource: [...data.folders, ...data.files],
      })
      let _this = this;
      data.files.forEach(function(value, index, array) {
        _this.state.mapdata = value;
    })
    })
    this.state.parent = id
  }
  getMessageData = () => {
    this.ds.url = `/messages/listener`;
      this.ds.get((data) => {
        console.log(data);
        this.setState({
          messageUrl : data.url
        })
      })
  }
  handleData(data) {
    console.log(123333333333);
    console.log(data);
    let result = JSON.parse(data);
    this.setState({count: this.state.count + result.movement});
  }
  showModal = () => {
    this.setState({visible: true});
  }
  handleOk = (e) => {
    this.setState({visible: false});
  } 
  handleCancel = (e) => {
    this.setState({visible: false});
  }
  clicknext = (item, key, keyPath) => {
    this.state.keys = item.key;
  }
  onclick = () => {
    // console.log(this.state.messageUrl);
  }
  render() {
    const mUrl = this.state.messageUrl;
    console.log(mUrl);
    console.log(777);
    return (
      <Layout>
        <Header className={styles.fine_header}>
          <Row>
            <Col span={10}>
              <div className={styles.fine_logo}></div>
            </Col>
            <Col span={14}>
              <Row>
                <Col span={10}>
                  <Row>
                    <Col span={10}>
                      <div className={`${styles.fine_clod_w} ${styles.fine_clod_active}`}>
                        <Link to="/main/userspace">
                          <div className={styles.fine_clod}></div>
                          <p className={styles.fine_clod_text}>云盘</p>
                        </Link>
                      </div>

                    </Col>

                    <Col span={12} offset={2}>
                      <div className={styles.fine_clod_h}>
                        <Link to="/main/userspace">
                          <div className={styles.fine_bus_word}></div>
                          <p className={styles.fine_clod_text}>企业文库</p>
                        </Link>
                      </div>
                      <div className={styles.fine_bo_right}></div>
                    </Col>
                  </Row>
                </Col>
                <Col span={14}>
                  <Row>
                    <Col span={9} offset={4}>
                    <Link to="/main/massage">
                      <div className={styles.fine_right_left}>
                      {/* <div onClick={this.onclick}>{mUrl}</div>
                      <strong>{this.state.count}</strong> 
                      <Websocket url = {mUrl}
                        onMessage={this.handleData.bind(this)}/> */}
                      </div>
                      </Link>
                      <div className={styles.fine_right_right}></div>
                    </Col>
                    <Col span={8} offset={3}>
                      <div className={styles.fine_close}></div>
                      <div className={styles.fine_enlarge}></div>
                      <div className={styles.fine_scale}></div>
                    </Col>
                  </Row>

                </Col>
              </Row>
            </Col>
          </Row>
        </Header>

        <Layout id="mainframe" className={styles.mainframe}>
          <Sider
            trigger={null}
            className={styles.fine_sider}
            style={{
            width: '142px',
            flex: '0 0 142px'
          }}>
            <Menu
              onClick={this.clicknext}
              defaultOpenKeys={['sub0']}
              defaultSelectedKeys={['1']}>
              <p className={styles.fine_word_space}>文档空间</p>
              <Menu.Item key="1">
                <Link to="/main/userspace">
                  <i
                    className={`${styles.fine_person_space} ${styles.fine_pub_sty} fine_person_space`}></i>
                  <span>个人空间</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="2">
                <Link to="/main/teamspace">
                  <i
                    className={`${styles.fine_team_space} ${styles.fine_pub_sty} fine_team_space`}></i>
                  <span>团队空间</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="3">
                <Link to="/main/deptspace">
                  <i
                    className={`${styles.fine_depart_space} ${styles.fine_pub_sty} fine_depart_space`}></i>
                  <span>部门空间</span>
                </Link>
              </Menu.Item>
              <div>
                <p className={styles.fine_word_space}>标签</p>
                <p>
                  <span
                    style={{
                    marginLeft: '10px',
                    color: '#EA5036',
                    fontSize: '14px'
                  }}>产品设计</span>
                  <span
                    style={{
                    marginLeft: '10px',
                    color: '#FCA716',
                    fontSize: '14px'
                  }}>竞品分析</span>
                </p>
              </div>
              <p className={styles.fine_word_space}>协作</p>
              <Menu.Item key="4">
                <Link to="/main/receiveshares">
                  <i
                    className={`${styles.fine_receive_file} ${styles.fine_pub_sty} fine_receive_file`}></i>
                  <span>我收到的文件</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="5">
                <Link to="/main/sendshares">
                  <i
                    className={`${styles.fine_share_file} ${styles.fine_pub_sty} fine_share_file`}></i>
                  <span>我共享的文件</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="6">
                <Link to="/main/transfertasks">
                  <i className={`${styles.fine_send_file} ${styles.fine_pub_sty} fine_send_file`}></i>
                  <span>我外发的文件</span>
                </Link>
              </Menu.Item>
              <p className={styles.fine_word_space}>工具</p>
              <Menu.Item key="7">
                <Link to="/main/trash">
                  <i className={`${styles.fine_send_file} ${styles.fine_pub_sty} fine_send_file`}></i>
                  <span>回收站</span>
                </Link>
              </Menu.Item>
              <Menu.Item key="8">
                <Link to="/main/backup">
                  <i className={`${styles.fine_back_up} ${styles.fine_pub_sty} fine_back_up`}></i>
                  <span>备份</span>
                </Link>
              </Menu.Item>
            </Menu>
          </Sider>
          <Content
            style={{
            padding: 0,
            position: 'relative',
            top: 0,
            bottom: 0,
            margin: '0px'
          }}>
            <DynamicSubRoute {...this.props}/>
          </Content>
        </Layout>
        {/* <Modal
          visible={this.state.visible}
          onCancel={this.handleCancel}
          footer={null}
          closable={false}
          style={{
          top: 70,
          left: 8 + '%'
        }}>
          <Innercollapse
          onclick={this.onclick}
          {...this.props}
          />
          <ul>
            <li>{this.state.mapdata.name}</li>
            <li>{this.state.mapdata.md5}</li>
            <li>{this.state.mapdata.objectId}</li>
            <li>{this.state.mapdata.menderName}</li>
            <li>{this.state.mapdata.createdAt}</li>
          </ul>
        </Modal> */}
      </Layout>
    )
  }
}

