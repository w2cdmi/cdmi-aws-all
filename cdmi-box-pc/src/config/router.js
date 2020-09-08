/**
 * 配置路由位置
 * @author 屈向前
 */
const RouterConfig = [
  {
    path: '/main',
    component: () => import('../routes/main'),
    routes: [
      {
        path: '/main/userspace',
        component: () => import('../routes/main/userspace'),
      },
      {
        path: '/main/teamspace',
        component: () => import('../routes/main/teamspace'),
      },
      {
	    path: '/main/teamspacefile',
	    component: () => import('../routes/main/teamspacefile'),
	  },
      {
        path: '/main/deptspace',
        component: () => import('../routes/main/deptspace'),
      },
      {
        path: '/main/deptspacefile',
        component: () => import('../routes/main/deptspacefile'),
      },
      {
        path: '/main/receiveshares',
        component: () => import('../routes/main/receiveshares'),
        
      },
      {
        path: '/main/sendshares',
        component: () => import('../routes/main/sendshares'),
      },
      {
        path: '/main/transfertasks',
        component: () => import('../routes/main/transfertasks'),
      },
      {
        path: '/main/trash',
        component: () => import('../routes/main/trash'),
      },
      {
        path: '/main/backup',
        component: () => import('../routes/main/backup'),
      },
      {
        path: '/main/massage',
        component: () => import('../routes/main/massage'),
      }
    ],
  },
]
export default RouterConfig
