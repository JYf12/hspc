<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="项目名称">
        <el-input v-model="form.projectName"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.addition" type="textarea"/>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="small" @click="addParamsSetting">新增包件</el-button>
      </el-form-item>
      <el-form-item label="包件信息">
        <el-table
          :data="form.tableData"
          style="width: 100%"
          class="list-table"
          size="mini"
          border>
          <el-table-column prop="index" label="序号" width="110">
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
                <el-input v-model="scope.row.packageName" placeholder="请输入包件名称"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="招标文件费" width="150">
            <template slot-scope="scope">
              <el-form-item
                label=""
                :prop="'form.tableData.' + scope.$index + '.price'"
                :rules="[{ required: true, message: '招标文件费不可为空', trigger: 'change' }, { type: 'number', message: '招标文件费必须为数字值'}]"
              >
                <el-input v-model="scope.row.price" placeholder="请输入招标文件费" @keyup="value=value.replace(/[^\d]/g,'')" @blur="value=value.replace(/[^\d]/g,'')">
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
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="50">
            <template slot-scope="scope">
              <el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item align="center">
        <el-button type="primary" @click="onSubmit">创建</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { addProject, addProjectFile } from '@/api/table'

export default {
  data() {
    return {
      form: {
        projectName: '',
        addition: '',
        // 动态表单数据
        tableData: [{
          packageName: '',
          price: '',
          filePath: '',
          fileName: '',
          startTime: '',
          endTime: ''
        }]
      },
      fileList: [{}]
    }
  },
  methods: {
    async onSubmit() {
      for (var i = 0; i < this.fileList.length; i++) {
        var filedata = new FormData()
        filedata.append('file', this.fileList[i].raw)
        await addProjectFile(filedata).then(response => {
          if (response.message === 'success') {
            this.form.tableData[i].filePath = response.data.filePath
            this.form.tableData[i].fileName = response.data.fileName
          } else {
            i = i - 1
          }
        })
      }
      addProject(this.form).then(response => {
        this.$message.success('创建成功！')
        this.$router.push('/table')
      })
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    addList() {
      this.form.tableData.push({
        packageName: '',
        price: '',
        filePath: ''
      })
      this.fileList.push({})
    },
    // 新增一行
    addParamsSetting() {
      this.addList()
    },

    // 删除当前行
    deleteRow(index) {
      this.form.tableData.splice(index, 1)
      this.fileList.splice(index, 1)
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
      }
    },

    handleRemove(file, filelist, index) {
      this.fileList[index] = {}
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

