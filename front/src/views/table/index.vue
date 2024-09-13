<template>
  <div class="app-container">
    <!--查询表单-->
    <el-card class="operate-container" shadow="never">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="项目编号">
          <el-input v-model="searchObj.projectId" placeholder="项目编号"/>
        </el-form-item>

        <el-form-item label="项目名称">
          <el-input v-model="searchObj.projectName" placeholder="项目名称"/>
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
        <el-button size="small" type="primary" round @click="goAddPage()">新增项目</el-button>
      </el-form>
    </el-card>

    <el-table
      v-loading="listLoading"
      :data="tableList"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="项目编号" width="250" align="center">
        <template slot-scope="scope">
          {{ scope.row.projectId }}
        </template>
      </el-table-column>
      <el-table-column label="项目名称" width="300" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.projectName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center">
        <template slot-scope="scope">
          {{ scope.row.addition }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="goDetailsPage(scope.row)">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
    />
  </div>
</template>

<script>
import { projectList } from '@/api/table'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      tableList: null,
      listLoading: true,
      total: 1, // 总记录数
      page: 1, // 页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询条件
      mutipleSelection: [] // 批量删除选中的记录列表
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      this.listLoading = true
      // getList().then(response => {
      //   console.log(response)
      //   this.list = response.data
      //   console.log(this.list)
      //   this.listLoading = false
      // })
      projectList(this.page, this.limit, this.searchObj).then(response => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    // 每页记录数改变，size：回调参数，表示当前选中的“每页条数”
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },

    // 改变页码，page：回调参数，表示当前选中的“页码”
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },

    // 重置表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },

    // 跳转新增页面
    goAddPage() {
      this.$router.push('/form')
    },

    // 跳转详情页面
    goDetailsPage(e) {
      this.$router.push({
        name: 'project_details',
        params: {
          id: e.id
        }
      })
    }

  }
}
</script>

