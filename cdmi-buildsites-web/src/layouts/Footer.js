import React, { Fragment } from 'react';
import { Layout, Icon } from 'antd';
import GlobalFooter from '@/components/GlobalFooter';

const { Footer } = Layout;
const FooterView = () => (
  <Footer style={{ padding: 0 }}>
    <GlobalFooter
      links={[
        {
          key: '泸州交投',
          title: '泸州交投',
          href: 'http://www.lzcijt.com',
          blankTarget: true,
        },
        {
          key: '聚数科技',
          title: <Icon type="github" />,
          href: 'https://www.storbox.cn',
          blankTarget: true,
        },
        {
          key: '四川路桥',
          title: '四川路桥',
          href: 'http://www.scrbc.com.cn',
          blankTarget: true,
        },
      ]}
      copyright={
        <Fragment>
          Copyright <Icon type="copyright" /> 2018 聚数科技成都有限公司
        </Fragment>
      }
    />
  </Footer>
);
export default FooterView;
