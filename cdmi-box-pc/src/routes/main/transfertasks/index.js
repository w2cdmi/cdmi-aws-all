import { Tabs, Button } from 'antd'
import { Component } from 'wolf'

const TabPane = Tabs.TabPane;

export default class Transfertasks extends Component {
  render() {
    return (
      <div>
        <Tabs defaultActiveKey="1">
          <TabPane tab="正在传输" key="1">
            Tab 1
          </TabPane>
          <TabPane tab="传输完成" key="2">
            <div>
              <Button>全部删除</Button>
            </div>
          </TabPane>
        </Tabs>
      </div>
    )
  }
}

