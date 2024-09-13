<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <el-badge :value="order.length">
            <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
            <i class="el-icon-caret-bottom" />
          </el-badge>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              主页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided @click.native="dialogTableVisible=true">
            <el-badge :value="order.length">
              消息
            </el-badge>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出登陆</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog title="待处理订单" :visible.sync="dialogTableVisible">
      <el-table :data="tableData">
        <el-table-column label="项目名称" width="150">
          <template slot-scope="scope">
            {{ scope.row.projectName }}
          </template>
        </el-table-column>
        <el-table-column label="投标单位" width="200">
          <template slot-scope="scope">
            {{ scope.row.company }}
          </template>
        </el-table-column>
        <el-table-column label="订单状态">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.status === 0">待付款</el-tag>
            <el-tag type="warning" v-if="scope.row.status === 1">已付款</el-tag>
            <el-tag type="success" v-if="scope.row.status === 2">已完成</el-tag>
            <el-tag type="info" v-if="scope.row.status === 3">已失效</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="onclickOrderDetails(scope.row.id)" type="text" size="small">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'order'
    ])
  },
  data() {
    return {
      dialogTableVisible: false,
      messageCounted: 0,
      orderInfo: [],
      currentPage: 1, // 当前页
      total: 0, // 总条数
      tableData: [], // 当前页码的表格数据
      pageSize: 5, // 当前页容量
      pageSizes: [5, 10, 15, 20]
    }
  },
  mounted() {
    this.dialogTableVisible = this.order.length > 0
    this.getList()
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    onclickOrderDetails(id) {
      this.dialogTableVisible = false
      this.$router.push({
        name: 'order_details',
        params: {
          id: id
        }
      })
    },
    // 切换每页条数容量
    handleSizeChange(val) {
      this.currentPage = 1
      this.pageSize = val
      this.getList()
    },
    // 切换当前页
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    },
    // 获取表格数据
    getList() {
      this.total = this.order.length
      this.tableData = this.getNeedArr(this.order, this.pageSize)[this.currentPage - 1] // 当前页的表格数据
    },
    // 前端处理分页
    getNeedArr(array, size) { // 获取所需指定长度分割的数组；参数1为用于分割的总数组，参数2为分割数组后每个小数组的长度
      const length = array.length
      // 判断不是数组，或者size没有设置，size小于1，就返回空数组
      if (!length || !size || size < 1) {
        return []
      }
      let index = 0 // 用来表示切割元素的范围start
      let resIndex = 0 // 用来递增表示输出数组的下标

      // 根据length和size算出输出数组的长度，并且创建它
      const result = new Array(Math.ceil(length / size))
      // 进行循环
      while (index < length) {
        // 循环过程中设置result[0]和result[1]的值，该值根据array.slice切割得到。
        result[resIndex++] = array.slice(index, (index += size))
      }
      // 输出到新数组
      return result
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
