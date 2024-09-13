<template>
  <div class="app-container">
    <el-form ref="form" :model="projectInfo" label-width="120px">
      <el-form-item>
        <el-button @click="handleUpdataForm" v-if="readStatus==='readonly'" type="primary">修改</el-button>
        <el-button @click="handleSubmitUpdata" v-if="readStatus===''" type="primary">确定</el-button>
        <el-button @click="cancelModification" v-if="readStatus===''" type="primary">取消</el-button>
        <el-button @click="onclickStopProject" v-if="this.projectInfo.status===1" type="warning">停止招标</el-button>
        <el-button @click="onclickStartProject" v-if="this.projectInfo.status===2" type="primary">开始招标</el-button>
        <el-button @click="onclickDeleteProject" type="danger">删除</el-button>
      </el-form-item>
      <el-form-item label="项目编号">
        <el-input :readonly="readStatus==='readonly'" v-model="projectInfo.projectId"/>
      </el-form-item>
      <el-form-item label="项目名称">
        <el-input :readonly="readStatus==='readonly'" v-model="projectInfo.projectName"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input :readonly="readStatus==='readonly'" v-model="projectInfo.addition" type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button v-if="readStatus===''" type="primary" size="small" @click="addParamsSetting">新增包件</el-button>
      </el-form-item>
      <el-form-item label="包件信息">
        <el-table
          :data="updateData.packageList"
          style="width: 100%"
          class="list-table"
          size="mini"
          border>
          <el-table-column prop="index" label="序号" width="50">
            <template slot-scope="scope">
              <el-form-item>
                {{ scope.$index + 1 }}
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="packageName" label="包件名称" width="150">
            <template slot-scope="scope">
              <el-form-item
                label=""
                :prop="'form.tableData.' + scope.$index + '.packageName'"
                :rules="[{ required: true, message: '包件名称不可为空', trigger: 'change' }]">
                <el-input :readonly="readStatus==='readonly'" v-model="scope.row.packageName" placeholder="请输入包件名称"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="招标文件费" width="150">
            <template slot-scope="scope">
              <el-form-item
                label=""
                :prop="'form.tableData.' + scope.$index + '.price'"
                :rules="[{ required: true, message: '招标文件费不可为空', trigger: 'change' }]"
              >
                <el-input :readonly="readStatus==='readonly'" v-model="scope.row.price" placeholder="请输入招标文件费" @keyup="value=value.replace(/[^\d]/g,'')" @blur="value=value.replace(/[^\d]/g,'')">
                  <template slot="append">元</template>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="startTime" label="开始时间">
            <template slot-scope="scope">
              <el-form-item
                label=""
                :prop="'form.tableData.' + scope.$index + '.startTime'"
                :rules="[{ required: true, message: '开始时间不可为空', trigger: 'change' }]">
                <el-date-picker
                  :readonly="readStatus==='readonly'"
                  v-model="scope.row.startTime"
                  type="datetime"
                  placeholder="选择开始时间"
                  value-format="yyyy-MM-dd HH:mm:ss">
                </el-date-picker>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="endTime" label="结束时间">
            <template slot-scope="scope">
              <el-form-item
                label=""
                :prop="'form.tableData.' + scope.$index + '.endTime'"
                :rules="[{ required: true, message: '结束时间不可为空', trigger: 'change' }]">
                <el-date-picker
                  :readonly="readStatus==='readonly'"
                  v-model="scope.row.endTime"
                  type="datetime"
                  placeholder="选择结束时间"
                  value-format="yyyy-MM-dd HH:mm:ss">
                </el-date-picker>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="filePath" label="招标文件" width="200">
            <template slot-scope="scope">
              <el-form-item
                label=""
                :prop="'form.tableData.' + scope.$index + '.fileList'"
                :rules="[{ required: true, message: '招标文件不可为空', trigger: 'change' }]"
              >
                <el-upload
                  v-if="readStatus===''"
                  action="http://localhost:8089"
                  class="upload-demo"
                  ref="upload"
                  :on-change="(file, filelist) => {handleChange(file, filelist, scope.$index)}"
                  :on-preview="handlePreview"
                  :on-remove="(file, filelist) => {handleRemove(file, filelist, scope.$index)}"
                  :limit="2"
                  :auto-upload="false">
                  选取文件
                </el-upload>
                <p v-if="readStatus==='readonly'">{{ scope.row.fileName }}</p>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column v-if="readStatus===''" label="操作" width="50">
            <template slot-scope="scope">
              <el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item>
        <el-button @click="downloadOrderExcel" type="primary">投标信息下载</el-button>
      </el-form-item>
      <el-form-item label="投标信息">
        <el-table
          :data="orderList"
          style="width: 100%"
          :row-class-name="tableRowClassName">
          <el-table-column
            prop="index"
            label="序号"
            width="80">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
            prop="orderPeopleName"
            label="投标联系人姓名"
            width="130">
            <template slot-scope="scope">
              {{ scope.row.name }}
            </template>
          </el-table-column>
          <el-table-column
            prop="orderPeoplePhone"
            label="投标联系人电话">
            <template slot-scope="scope">
              {{ scope.row.phoneNum }}
            </template>
          </el-table-column>
          <el-table-column
            prop="unitName"
            label="投标单位">
            <template slot-scope="scope">
              {{ scope.row.unitName }}
            </template>
          </el-table-column>
          <el-table-column
            prop="total"
            label="总金额（元）">
            <template slot-scope="scope">
              {{ scope.row.total }}
            </template>
          </el-table-column>
          <el-table-column
            prop="downloadStatus"
            label="已下载包件"
            :filters="[{text: '否', value: 0}, {text: '是', value: 1}]"
            :filter-method="filterDownload">
            <template slot-scope="scope">
              <el-tag
                v-if="scope.row.downloadStatus === 0"
                :key="'否'"
                :type="'danger'"
                effect="dark">
                否
              </el-tag>
              <el-tag
                v-if="scope.row.downloadStatus === 1"
                :key="'是'"
                :type="'success'"
                effect="dark">
                是
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="订单状态"
            width="150"
            :filters="[{text: '待付款', value: 0}, {text: '已付款', value: 1}, {text: '已完成', value: 2}, {text: '已失效', value: 3}, {text: '未通过', value: 4}]"
            :filter-method="filterStatus">
            <template slot-scope="scope">
              <el-tag
                v-if="scope.row.status === 0"
                :key="'待付款'"
                :type="'warning'"
                effect="dark">
                待付款
              </el-tag>
              <el-tag
                v-if="scope.row.status === 1"
                :key="'已付款'"
                :type="'primary'"
                effect="dark">
                已付款
              </el-tag>
              <el-tag
                v-if="scope.row.status === 2"
                :key="'已完成'"
                :type="'success'"
                effect="dark">
                已完成
              </el-tag>
              <el-tag
                v-if="scope.row.status === 3"
                :key="'已失效'"
                :type="'info'"
                effect="dark">
                已失效
              </el-tag>
              <el-tag
                v-if="scope.row.status === 4"
                :key="'未通过'"
                :type="'danger'"
                effect="dark">
                未通过
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="onclickOrderDetails(scope.row)" type="text" size="small">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="page"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="limit"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            style="padding: 30px 0; text-align: center;">
          </el-pagination>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {
  getProjectInfo,
  getOrderList,
  postStopProject,
  deleteProject,
  getOrderPackageCount,
  postStartProject
} from '@/api/project_details'
import { addProjectFile, updateProject } from '@/api/table'
import moment from 'moment'

export default {
  data() {
    return {
      queryData: {
        id: 0
      },
      projectInfo: null,
      orderQueryData: {
        username: '',
        id: 0
      },
      orderList: null,
      page: 1,
      limit: 5,
      total: 0,
      readStatus: 'readonly',
      fileList: [],
      updateData: {
        projectName: '',
        addition: '',
        packageList: [],
        status: [], // 0 新增， 1 更新， 2 不变
        projectId: ''
      }
    }
  },
  created() {
    this.queryData.id = this.$route.params.id
    this.orderQueryData.id = this.$route.params.id
    this.getProjectDetails()
    this.getOrderListPage()
  },
  methods: {
    async getProjectDetails() {
      this.listLoading = true
      await getProjectInfo(this.queryData).then(response => {
        this.projectInfo = response.data
        this.updateData.packageList = response.data.packageList
        const indexNum = this.updateData.packageList.length
        for (let i = 0; i < indexNum; i++) {
          this.$set(this.updateData.packageList[i], 'startTime', new Date(this.updateData.packageList[i].startTime))
          this.$set(this.updateData.packageList[i], 'endTime', new Date(this.updateData.packageList[i].endTime))
          this.updateData.status.push(2)
          this.fileList.push({})
        }
        this.listLoading = false
      })
    },
    getOrderListPage() {
      getOrderList(this.page, this.limit, this.orderQueryData).then(response => {
        this.orderList = response.data.records
        this.total = response.data.total
      })
    },
    handleSizeChange(val) {
      this.limit = val
      this.getOrderListPage()
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      this.page = val
      this.getOrderListPage()
      console.log(`当前页: ${val}`)
    },
    handleUpdataForm() {
      this.readStatus = ''
    },
    async handleSubmitUpdata() {
      this.readStatus = 'readonly'
      this.updateData.projectId = this.projectInfo.id
      this.updateData.projectName = this.projectInfo.projectName
      this.updateData.addition = this.projectInfo.addition
      this.updateData.id = this.projectInfo.projectId
      for (var i = 0; i < this.fileList.length; i++) {
        this.updateData.packageList[i].startTime = moment(new Date(this.updateData.packageList[i].startTime)).format('YYYY-MM-DD HH:mm:ss')
        this.updateData.packageList[i].endTime = moment(new Date(this.updateData.packageList[i].endTime)).format('YYYY-MM-DD HH:mm:ss')
        if (this.updateData.status[i] === 2) {
          continue
        }
        var filedata = new FormData()
        filedata.append('file', this.fileList[i].raw)
        await addProjectFile(filedata).then(response => {
          if (response.message === 'success') {
            this.updateData.packageList[i].filePath = response.data.filePath
            this.updateData.packageList[i].fileName = response.data.fileName
          } else {
            i = i - 1
          }
        })
      }
      updateProject(this.updateData).then(response => {
        this.$message.success('更新成功！')
        this.$router.push({
          name: 'project_details',
          params: {
            id: this.projectInfo.id
          }
        })
      })
    },
    cancelModification() {
      this.$router.go(0)
    },
    onclickStopProject() {
      postStopProject(this.projectInfo.id).then(res => {
        console.log(res)
        this.$router.go(0)
      })
    },
    onclickStartProject() {
      postStartProject(this.projectInfo.id).then(res => {
        console.log(res)
        this.$router.go(0)
      })
    },
    onclickDeleteProject() {
      deleteProject(this.projectInfo.id).then(res => {
        this.$router.push('/table')
        console.log(res)
      })
    },
    onclickOrderDetails(e) {
      this.$router.push({
        name: 'order_details',
        params: {
          id: e.id
        }
      })
    },
    addList() {
      this.updateData.packageList.push({
        id: '',
        fileName: '',
        filePath: '',
        packageName: '',
        price: '',
        projectId: this.projectInfo.id,
        startTime: '',
        endTime: ''
      })
      this.updateData.status.push(2)
      this.fileList.push({})
    },
    // 新增一行
    addParamsSetting() {
      this.addList()
    },

    // 删除当前行
    deleteRow(index) {
      getOrderPackageCount(this.updateData.packageList[index].id).then(res => {
        if (res.data > 0) {
          this.$message.error({
            message: '该包件已被购买，无法删除！',
            toString: () => this.message
          })
        } else {
          this.updateData.packageList.splice(index, 1)
          this.updateData.status.splice(index, 1)
          this.fileList.splice(index, 1)
        }
      })
    },
    handleChange(file, filelist, index) {
      const fileSize = Number(file.size / 1024 / 1024)
      if (fileSize > 2048) {
        this.$message.warning('文件大小不能超过2GB，请重新上传。')
        filelist.splice(filelist.length - 1, 1)
        return
      }
      while (filelist.length > 1) {
        filelist.splice(0, 1)
      }
      if (file) {
        this.fileList[index] = filelist[filelist.length - 1]
        if (this.updateData.packageList[index].filePath === '') {
          this.updateData.status[index] = 0
        } else {
          this.updateData.status[index] = 1
        }
      }
    },

    handleRemove(file, filelist, index) {
      this.fileList[index] = {}
    },
    handlePreview(file) {
      console.log(file)
    },
    downloadOrderExcel() {
      const url = process.env.VUE_APP_BASE_API + '/api/order/downloadOrderList/' + this.projectInfo.id
      const a = document.createElement('a')
      a.href = url
      a.click()
    },
    filterDownload(value, row) {
      return row.downloadStatus === value
    },
    filterStatus(value, row) {
      return row.status === value
    }
  }
}
</script>

<style scoped>

</style>
