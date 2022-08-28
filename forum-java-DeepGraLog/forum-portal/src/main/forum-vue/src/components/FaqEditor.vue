<style scoped>
.ivu-btn-success,
.ivu-btn-info {
  padding-left: 25px;
  padding-right: 25px;
  font-weight: 400;
  padding: .375rem 1.37rem;
  font-size: 1rem;
  line-height: 1.5;
}
.ivu-select-selection:hover,
.ivu-checkbox-checked .ivu-checkbox-inner,
.ivu-btn-info:hover,
.ivu-btn-small:hover,
.ivu-btn-info,
.ivu-btn-primary,
.ivu-btn-success {
  border: 1px solid #28a745;
}
.ivu-btn-small:hover,
.ivu-btn-info,
.ivu-btn-text:hover {
  color: #28a745;
}
.ivu-checkbox-checked .ivu-checkbox-inner,
.ivu-btn-primary {
  background-color: #28a745;
}
.ivu-btn-info:hover {
  color: #fff;
}
.ivu-btn-small:hover,
.ivu-btn-info {
  background-color: #fff;
}
.ivu-btn-primary,
.ivu-btn-success,
.ivu-checkbox-checked .ivu-checkbox-inner ,
.ivu-btn-info:hover {
  background-color: #28a745;
}
.article-box-title {
  width: 100%;
  height: 80px;
}
.article-box--title-home,
.article-box-title-input,
.article-box-foot-publish,
.article-box-foot-tag {
  display: block;
  height: 100%;
  border: 0;
  float: left;
}
.article-box-title-input {
  width: calc(100% - 150px);
  padding-left: 10px;
  font-size: 30px;
}
.article-box-title-input:focus {
  outline: none;
}
.article-box--title-home {
  width: 150px;
  padding-top: 22px;
  text-align: center;
}
.article-box-content {
  width: 100%;
  height: calc(100% - 140px);
}
.article-box-foot {
  width: 100%;
  height: 60px;
}
.article-box-foot-tag {
  width: calc(100% - 350px);
  padding-top: 18px;
  padding-left: 15px;
}
.article-box-foot-publish {
  width: 350px;
  padding-top: 10px;
  text-align: center;
}
.tag-group {
  margin-bottom: 10px;
  line-height: 32px;
  font-weight: bold;
  font-size: 14px;
  color: #333;
  border-bottom: 1px solid #EEE;
}
@media (max-width: 768px) {
  .article-box-title-input,
  .article-box-foot-tag,
  .article-box-foot-type,
  .article-box-foot-publish,
  .article-box--title-home {
    width: 100%;
    text-align: left;
    padding-left: 10px;
  }
}
</style>
<template>
  <div class="article-editor article-box" :style="{height: containerHeight}">
    <Modal
        title="select the tag"
        v-model="isShowTagSelectModel"
        :mask-closable="false">
      <div v-for="(tagGroup, index) in showTags" :key="index">
        <h3 class="tag-group">{{tagGroup.groupName}}</h3>
        <CheckboxGroup v-model="selectedTags" @on-change="tagChange">
          <Checkbox border v-for="tag in tagGroup.tags" :key="tag.id" :label="tag.name"></Checkbox>
        </CheckboxGroup>
      </div>
    </Modal>
    <div class="article-box-title">
      <div class="article-box--title-home">
        <Button @click="goHome" type="info">back to homepage</Button>
      </div>
      <input v-model="article.title" class="article-box-title-input" placeholder="Enter question description"/>
    </div>
    <mavon-editor
      class="article-box-content"
      placeholder="Enter question content, markdown format"
      :style="{zIndex:0}"
      :toolbars="editorSetting"
      v-model="article.markdownContent"
      ref=md
      @save="updateArticle"
      @imgAdd="addImg"/>
    <div class="article-box-foot">
      <div class="article-box-foot-tag">
        <Tag v-for="tag in article.tags"
          :key="tag.id"
          :name="tag.name"
          @on-close="delTag"
          closable
          type="border"
          color="success">{{ tag.name }}</Tag>
        <Button type="dashed" size="small" @click="showTagSelectModel">add the tag</Button>
      </div>
      <div class="article-box-foot-publish">
        <Button @click="updateArticle" type="info">Save and continue editing</Button>&nbsp;&nbsp;&nbsp;&nbsp;
        <Button @click="saveArticle" type="success">release</Button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'FaqEditor',
  data () {
    return {
      isShowTagSelectModel: false,
      containerHeight: '500px',
      showTags: [],
      tags: [],
      selectedTags: [],
      article: {
        id: null,
        title: '',
        markdownContent: '',
        tags: []
      },
      editorSetting: {
        bold: true, //
        italic: true, //
        header: true, //
        underline: true, //
        strikethrough: true, //
        mark: true, //
        superscript: true, //
        subscript: true, //
        quote: true, //
        ol: true, //
        ul: true, //
        link: true, //
        imagelink: true, //
        code: true, // code
        table: true, //
        fullscreen: true, //
        readmodel: true, //
        // htmlcode: true, //
        help: true, //
        /* 1.3.5 */
        undo: true, //
        redo: true, //
        // trash: true, //
        save: true, //
        /* 1.4.2 */
        navigation: true, //
        /* 2.1.8 */
        alignleft: true, //
        aligncenter: true, //
        alignright: true, //
        /* 2.2.1 */
        // subfield: true, //
        preview: true //
      }
    }
  },
  methods: {
    goHome () {
      location.href = '/faq'
    },
    showTagSelectModel () {
      this.isShowTagSelectModel = true
    },
    loadArticle (id) {
      this.$Loading.start()
      this.$http.post(`/faq-rest/${id}`).then(res => {
        if (res.code !== 200) {
          this.$Loading.error()
          return
        }
        this.$Loading.finish()
        this.article = res.data
        document.title = this.article.title
        this.tagChecked()
        this.typeChecked()
      })
    },
    tagChange (checkedNameList) {
      if (checkedNameList.length > 5) {
        this.$Message.error('A maximum of 5 labels can be selected')
        const selectedTagNames = []
        for (let i = 0; i < this.article.tags.length; i++) {
          selectedTagNames.push(this.article.tags[i].name)
        }
        this.selectedTags = selectedTagNames
        return
      }
      const selectedTags = []
      for (let i = 0; i < this.tags.length; i++) {
        for (let j = 0; j < checkedNameList.length; j++) {
          if (this.tags[i].name === checkedNameList[j]) {
            selectedTags.push(this.tags[i])
          }
        }
      }
      this.article.tags = selectedTags
    },
    updateArticle () {
      this.postArticle('update success', () => {})
    },
    saveArticle () {
      this.postArticle('save success', () => {
        location.href = '/faq'
      })
    },
    postArticle (message, callback) {
      if (!this.preCheck()) {
        return
      }
      const headImgs = this.getHeadImgs()
      this.$Loading.start()
      this.$http.post(`/faq-rest/save`, {
        id: this.article.id,
        typeId: this.article.typeId,
        title: this.article.title,
        contentType: 'MARKDOWN',
        markdownContent: this.article.markdownContent,
        htmlContent: this.$refs.md.d_render,
        tagIds: this.getTagIds(),
        headImg: headImgs.length > 0 ? JSON.stringify(headImgs) : ''
      }).then(res => {
        if (res.code !== 200) {
          this.$Loading.error()
          this.$Message.error(res.message)
          return
        }
        this.$Loading.finish()
        this.article.id = res.data
        this.$Message.success(message)
        callback()
      })
    },
    preCheck () {
      if (!this.article.title) {
        this.$Message.error('The title cannot be empty')
        return false
      }
      if (this.article.title.length > 40) {
        this.$Message.error('The title can be no longer than 40 characters')
        return false
      }
      if (!this.article.markdownContent) {
        this.$Message.error('The content cannot be empty')
        return false
      }
      if (!this.article.tags || this.article.tags.length === 0) {
        this.$Message.error('The tag cannot be empty')
        return false
      }
      return true
    },
    tagChecked () {
      if (this.tags.length === 0) {
        return
      }
      for (let i = 0; i < this.tags.length; i++) {
        for (let j = 0; j < this.article.tags.length; j++) {
          if (this.article.tags[j].id === this.tags[i].id) {
            this.selectedTags.push(this.tags[i].name)
          }
        }
      }
    },
    getTagIds () {
      const tagIds = []
      for (let i = 0; i < this.article.tags.length; i++) {
        tagIds.push(this.article.tags[i].id)
      }
      return tagIds
    },
    getHeadImgs () {
      const pattern = /!\[(.*?)\]\((.*?)\)/mg
      const result = []
      let matcher
      while ((matcher = pattern.exec(this.article.markdownContent)) !== null) {
        result.push({
          alt: matcher[1],
          url: matcher[2]
        })
      }
      return result
    },
    addImg (pos, $file) {
      var formdata = new FormData()
      formdata.append('image', $file)
      this.$Loading.start()
      axios({
        url: '/file-rest/upload',
        method: 'post',
        data: formdata,
        headers: {
          'Content-Type': 'multipart/form-data',
          'token': localStorage.getItem('token')
        }
      }).then((res) => {
        if (res.code !== 200) {
          this.$Loading.error()
          return
        }
        this.$Loading.finish()
        this.$refs.md.$img2Url(pos, res.data)
      })
    },
    delTag (event, name) {
      for (let i = 0; i < this.article.tags.length; i++) {
        if (this.article.tags[i].name === name) {
          this.article.tags.splice(i, 1)
        }
      }
    },
    loadTags () {
      this.$Loading.start()
      this.$http.post(`/tag-rest/all`).then(res => {
        if (res.code !== 200) {
          this.$Loading.error()
          return
        }
        this.$Loading.finish()
        const allTags = res.data
        this.tags = res.data
        const showTags = []
        for (let i = 0; i < allTags.length; i++) {
          let hasGroup = false
          for (let j = 0; j < showTags.length; j++) {
            if (allTags[i].groupName === showTags[j].groupName) {
              showTags[j].tags.push(allTags[i])
              hasGroup = true
            }
          }
          if (!hasGroup) {
            showTags.push({
              groupName: allTags[i].groupName,
              tags: [allTags[i]]
            })
          }
        }
        this.showTags = showTags
        this.tagChecked()
      })
    }
  },
  created () {
    let allHeight = window.innerHeight
    this.containerHeight = allHeight + 'px'
    this.loadTags()
    if (this.$route.params.id) {
      this.loadArticle(this.$route.params.id)
    } else {
      document.title = 'ask questions'
      this.article.markdownContent = `#

## 1.1

- xxxx;
- xxxx;

## 1.2

1. xxxx;
2. xxxx;`
    }
  }
}
</script>
