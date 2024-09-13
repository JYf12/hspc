<template>
  <div class="app-container">
    <el-form>
      <el-form-item>
        <el-dialog
          title="提示"
          :visible.sync="altPasDiaVis"
          width="30%">
          <el-form ref="form" :model="altPasData" label-width="120px">
            <el-form-item label="新密码:" :rules="[{ required: true, message: '请输入新密码' }]">
              <el-input v-model="altPasData.newPassword" type="password" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="确认新密码:" :rules="[{ pattern: adminUser.userPassword === adminUser.checkPassword, required: true, message: '两次输入密码不一致'}]">
              <el-input v-model="altPasData.checkNewPas" type="password" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="altPasDiaVis = false">取 消</el-button>
              <el-button type="primary" @click="onclickAlterPassword">确 定</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
<!--        <el-dialog-->
<!--          title="提示"-->
<!--          :visible.sync="altScopeDiaVis"-->
<!--          width="30%">-->
<!--          <el-form ref="form" :model="altScopeData" label-width="80px">-->
<!--            <el-form-item label="管理范围" :rules="[{ required: true }]">-->
<!--              <el-radio v-model="altScopeData.manScope" label="0">个人</el-radio>-->
<!--              <el-radio v-model="altScopeData.manScope" label="1">全部</el-radio>-->
<!--            </el-form-item>-->
<!--            <el-form-item>-->
<!--              <el-button @click="altScopeDiaVis = false">取 消</el-button>-->
<!--              <el-button type="primary" @click="onclickAlterUser">确 定</el-button>-->
<!--            </el-form-item>-->
<!--          </el-form>-->
<!--        </el-dialog>-->
        <el-button v-if="role === '[admin]'" type="primary" @click="dialogVisible = true" plain>新增管理员用户</el-button>
        <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="30%">
          <el-form ref="form" :model="adminUser" label-width="80px">
            <el-form-item label="姓名" :rules="[{ required: true, message: '请输入姓名' }]">
              <el-input v-model="adminUser.username" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="账号" :rules="[{ required: true, message: '请输入账号' }]">
              <el-input v-model="adminUser.userAccount" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="密码" :rules="[{ required: true, message: '请输入密码' }]">
              <el-input v-model="adminUser.userPassword" type="password" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" :rules="[{ pattern: adminUser.userPassword === adminUser.checkPassword, required: true, message: '两次输入密码不一致'}]">
              <el-input v-model="adminUser.checkPassword" type="password" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="权限" :rules="[{ required: true }]">
              <el-radio v-model="adminUser.isAdmin" label="0">普通管理员</el-radio>
              <el-radio v-model="adminUser.isAdmin" label="1">超级管理员</el-radio>
            </el-form-item>
<!--            <el-form-item label="管理范围" :rules="[{ required: true }]">-->
<!--              <el-radio v-model="adminUser.manScope" label="0">个人</el-radio>-->
<!--              <el-radio v-model="adminUser.manScope" label="1">全部</el-radio>-->
<!--            </el-form-item>-->
            <el-form-item>
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="onclickAddUser">确 定</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </el-form-item>
      <el-form-item>
        <el-table
          v-loading="listLoading"
          :data="list"
          element-loading-text="Loading"
          border
          fit
          highlight-current-row
        >
          <el-table-column align="center" label="ID" width="95">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="用户姓名">
            <template slot-scope="scope">
              {{ scope.row.username }}
            </template>
          </el-table-column>
          <el-table-column label="用户账户" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.userAccount }}</span>
            </template>
          </el-table-column>
          <el-table-column label="用户类型" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.isSuperadmin == '1'">超级管理员</span>
              <span v-if="scope.row.isSuperadmin == '0'">普通管理员</span>
            </template>
          </el-table-column>
<!--          <el-table-column label="管理权限" align="center">-->
<!--            <template slot-scope="scope">-->
<!--              <span v-if="scope.row.manScope == '1'">全部</span>-->
<!--              <span v-if="scope.row.manScope == '0'">个人</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column label="用户管理" align="center" v-if="role === '[admin]'">
            <template slot-scope="scope">
              <el-button @click.native.prevent="openAlterPas(scope.row.id)" type="text" size="small">修改密码</el-button>
<!--              <el-button @click.native.prevent="openAlterUser(scope.row.id)" type="text" size="small">更改权限</el-button>-->
              <el-button @click.native.prevent="onclickDeleteUser(scope.row.id)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getUserList, postAddAdminUser, postDeleteAdminUser, postAlterAdminUser, postAlterPassword } from '@/api/usertable'
import { mapGetters } from 'vuex'

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
  computed: {
    ...mapGetters([
      'role'
    ])
  },
  data() {
    return {
      list: null,
      listLoading: true,
      adminUser: {
        username: '',
        userAccount: '',
        userPassword: '',
        checkPassword: '',
        isAdmin: ''
        // manScope: ''
      },
      dialogVisible: false,
      altScopeDiaVis: false,
      altPasDiaVis: false,
      // altScopeData: {
      //   id: 0,
      //   manScope: ''
      // },
      altPasData: {
        id: 0,
        newPassword: '',
        checkNewPas: ''
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getUserList().then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    onclickAddUser() {
      postAddAdminUser(this.adminUser).then(res => {
        this.dialogVisible = false
        this.$router.go(0)
      })
    },
    onclickDeleteUser(id) {
      postDeleteAdminUser(id).then(res => {
        this.$router.go(0)
        // this.$router.replace({path:this.$router.currentRoute.path,query: this.$router.currentRoute.query })
      })
    },
    onclickAlterUser() {
      postAlterAdminUser(this.altScopeData).then(res => {
        this.$router.go(0)
      })
    },
    onclickAlterPassword(id) {
      postAlterPassword(this.altPasData).then(res => {
        this.$router.go(0)
      })
    },
    openAlterUser(id) {
      this.altScopeData.id = id
      this.altScopeDiaVis = true
    },
    openAlterPas(id) {
      this.altPasData.id = id
      this.altPasDiaVis = true
    }
  }
}
</script>
