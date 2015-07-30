/* 带有CheckBox的树的选中的插件
 * author:jn_nian
 * createTime:2010-10-24 21:46
 * usage: Ext3使用 plugins : ['treecheck']或plugins:[new Ext.ux.TreePanelCheck()]
 * Ext2使用 ：plugins:[new Ext.ux.TreePanelCheck()]
 *******************************************************************/

Ext.ux.TreePanelCheck = Ext.extend(Ext.tree.TreePanel, {
	initComponent : function() {
		var me = this;
		var rootNode = me.getRootNode();

		me.on('expandnode', this.doLazyCheck, rootNode);
		me.on('checkchange', this.handlerCheck, this);
	},

	// 检查子结点选中的情况
	doChildHasChecked : function(node) {
		var childNodes = node.childNodes;
		var checkedNum = 0;
		if (childNodes || childNodes.length > 0) {
			for (var i = 0; i < childNodes.length; i++) {
				if (childNodes[i].getUI().checkbox.checked) {
					checkedNum = checkedNum + 1;
				}
			}
		}
		return checkedNum;
	},

	// 父节点选中
	doParentCheck : function(node, checked) {
		var checkbox = node.getUI().checkbox;
		if (typeof checkbox == 'undefined')
			return false;
		node.getUI().checkbox.indeterminate = false;
		node.getUI().checkbox.checked = checked;

		node.attributes.checked = checked;
		var childChecked = this.doChildHasChecked(node);
		if (childChecked == node.childNodes.length) {
			node.getUI().checkbox.checked = true;
			node.getUI().checkbox.indeterminate = false;
		} else if (childChecked == 0) {
			var indeterminate = false;
			node.eachChild(function(child) {
				if (child.getUI().checkbox.indeterminate) {
					indeterminate = true;
					return false;
				}
			});
			node.getUI().checkbox.checked = false;
			node.getUI().checkbox.indeterminate = indeterminate;
		} else {
			node.getUI().checkbox.checked = false;
			node.getUI().checkbox.indeterminate = true; // 半选中状态
		}

		node.getOwnerTree().fireEvent('check', node, checked);
		var parentNode = node.parentNode;
		if (parentNode !== null) {
			this.doParentCheck(parentNode, checked);
		}
	},

	handlerCheck : function(node, checked) {
		var parentNode = node.parentNode;
		if (!Ext.isEmpty(parentNode)) {
			this.doParentCheck(parentNode, checked);
		}
		node.attributes.checked = checked;
		// node.expandChildNodes(true);
		node.eachChild(function(child) {
			child.ui.toggleCheck(checked);
			child.attributes.checked = checked;
			child.fireEvent('checkchange', child, checked);
		});
	},

	// 延迟加载选中
	doLazyCheck : function(node) {
		if (!Ext.isEmpty(node.parentNode)) {
			var nodeChecked = node.getUI().checkbox.checked;
			// node.expandChildNodes(true);
			node.eachChild(function(child) {
				child.getUI().checkbox.checked = nodeChecked;
			});
		}
	},

	getPType : function() {
		return this.ptype;
	}
});
Ext.preg('treecheck', Ext.ux.TreePanelCheck);
/*
Ext.ux.TreeCheckNodeUI = function() {
	// 多选: 'multiple'(默认)
	// 单选: 'single'
	// 级联多选: 'cascade'(同时选父和子);'parentCascade'(选父);'childCascade'(选子)
	this.checkModel = 'multiple';
	// only leaf can checked
	this.onlyLeafCheckable = false;
	Ext.ux.TreeCheckNodeUI.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.ux.TreeCheckNodeUI, Ext.tree.TreeNodeUI, {
	renderElements : function(n, a, targetNode, bulkRender) {
		var tree = n.getOwnerTree();
		this.checkModel = tree.checkModel || this.checkModel;
		this.onlyLeafCheckable = tree.onlyLeafCheckable || false;
		// add some indent caching, this helps performance when
		// rendering a large tree
		this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent()
				: '';
		// var cb = typeof a.checked == 'boolean';
		var cb = (!this.onlyLeafCheckable || a.leaf);
		var href = a.href ? a.href : Ext.isGecko ? "" : "#";
		var buf = [
				'<li class="x-tree-node"><div ext:tree-node-id="',
				n.id,
				'" class="x-tree-node-el x-tree-node-leaf x-unselectable ',
				a.cls,
				'" unselectable="on">',
				'<span class="x-tree-node-indent">',
				this.indentMarkup,
				"</span>",
				'<img src="',
				this.emptyIcon,
				'" class="x-tree-ec-icon x-tree-elbow" />',
				'<img class="x-tree-node-cb" src="js/images/'
						+ n.attributes.checked + '.gif">',
				'<a hidefocus="on" class="x-tree-node-anchor" href="', href,
				'" tabIndex="1" ',
				a.hrefTarget ? ' target="' + a.hrefTarget + '"' : "",
				'><span unselectable="on">', n.text, "</span></a></div>",
				'<ul class="x-tree-node-ct" style="display:none;"></ul>',
				"</li>" ].join('');
		var nel;
		if (bulkRender !== true && n.nextSibling
				&& (nel = n.nextSibling.ui.getEl())) {
			this.wrap = Ext.DomHelper.insertHtml("beforeBegin", nel, buf);
		} else {
			this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf);
		}
		this.elNode = this.wrap.childNodes[0];
		this.ctNode = this.wrap.childNodes[1];
		var cs = this.elNode.childNodes;
		this.indentNode = cs[0];
		this.ecNode = cs[1];
		var index = 2;
		if (cb) {
			this.checkbox = cs[2];
			Ext.fly(this.checkbox).on('click',
					this.onCheck.createDelegate(this, [ null ]));
			index++;
		}
		this.anchor = cs[index];
		this.textNode = cs[index].firstChild;
	},
	// private
	onCheck : function() {
		this.check(this.toggleCheck(this.node.attributes.checked));
	},
	check : function(checked) {
		var n = this.node;
		n.attributes.checked = checked;
		this.setNodeIcon(n);
		this.childCheck(n, n.attributes.checked);
		this.parentCheck(n);
	},
	parentCheck : function(node) {
		var currentNode = node;
		while ((currentNode = currentNode.parentNode) != null) {
			if (!currentNode.getUI().checkbox)
				continue;
			var part = false;
			var sel = 0;
			Ext.each(currentNode.childNodes, function(child) {
				if (child.attributes.checked == 'all')
					sel++;
				else if (child.attributes.checked == 'part') {
					part = true;
					return false;
				}
			});
			if (part)
				currentNode.attributes.checked = 'part';
			else {
				var selType = null;
				if (sel == currentNode.childNodes.length) {
					currentNode.attributes.checked = 'all';
				} else if (sel == 0) {
					currentNode.attributes.checked = 'none';
				} else {
					currentNode.attributes.checked = 'part';
				}
			}
			this.setNodeIcon(currentNode);
		}
		;
	},
	setNodeIcon : function(n) {
		if (n.getUI() && n.getUI().checkbox)
			n.getUI().checkbox.src = 'js/images/' + n.attributes.checked
					+ '.gif';
	},
	// private
	childCheck : function(node, checked) {
		// node.expand(true,true);
		if (node.childNodes)
			Ext.each(node.childNodes, function(child) {
				child.attributes.checked = checked;
				this.setNodeIcon(child);
				this.childCheck(child, checked);
			}, this);
	},
	toggleCheck : function(value) {
		return (value == 'all' || value == 'part') ? 'none' : 'all';
	}
});
*/