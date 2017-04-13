package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorParameterBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.userinterface.ClassUserInterface;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.ui.web.api.WebNavigationManager;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

@Getter @Setter
public class Tree extends AbstractTree<TreeNode, HierarchyNode> implements Serializable {

	private static final long serialVersionUID = 763364839529624006L;
	
	private CascadeStyleSheet css = new CascadeStyleSheet();
	private Listener<TreeNode, HierarchyNode> listener;
	
	private DefaultDiagramModel model;
	
	public Tree() {
		treeListeners.add(listener = new Listener.Adapter.Default<TreeNode, HierarchyNode>(){
			private static final long serialVersionUID = 1L;
			@Override
			public TreeNode createRootNode() {
				return new DefaultTreeNode(ROOT, null);
			}
			
			@Override
			public TreeNode createNode(HierarchyNode model, TreeNode parent) {
				return new DefaultTreeNode(model, parent);
			}
			
			@Override
			public HierarchyNode createModel(Object node) {
				return new HierarchyNode(node);
			}
			
			@Override
			public void expandNode(TreeNode node) {
				node.setExpanded(Boolean.TRUE);
			}
			
			@Override
			public void selectNode(TreeNode node) {
				node.setSelected(Boolean.TRUE);
			}
			
			@Override
			public void collapseNode(TreeNode node) {
				node.setExpanded(Boolean.FALSE);
			}
			
			@Override
			public TreeNode parentNode(TreeNode node) {
				return node.getParent();
			}
			
			@Override
			public Collection<TreeNode> nodeChildren(TreeNode node) {
				return node.getChildren();
			}
			
			@Override
			public HierarchyNode nodeModel(TreeNode node) {
				return (HierarchyNode) node.getData();
			}
			@Override
			public Boolean isLeaf(TreeNode node) {
				return node.isLeaf();
			}
			
			@Override
			public Object getRedirectionObject(TreeNode node) {
				return selectedAs(AbstractIdentifiable.class);
			}
			
			@Override
			public String getRedirectToViewId(TreeNode node,Crud crud,Object object) {
				Boolean edit = Crud.isCreateOrUpdate(crud);
				HierarchyNode model = nodeModel(selected);
				String v_outcome = model == null ? (edit ? editViewId : consultViewId) : (edit ? model.getEditViewId() : model.getConsultViewId());
				if(StringUtils.isBlank(v_outcome))
					v_outcome = edit ? editViewId : consultViewId;
				//Object data = getRedirectionObject(node);
				if(object==null)
					;
				else{
					if(StringUtils.isBlank(v_outcome)){
						ClassUserInterface userInterface = UIManager.getInstance().businessEntityInfos(object.getClass()).getUserInterface();
						v_outcome = edit ? userInterface.getEditViewId() : userInterface.getConsultViewId();
					}
				}
				return v_outcome;
			}
			
			@Override
			public Object[] getRedirectToParameters(TreeNode node,Crud crud,Object object) {
				Object nodeObject = ((HierarchyNode)node.getData()).getData();
				Object[] parameters = null;
				if(object==null)
					;
				else{
					parameters = new Object[]{UniformResourceLocatorParameter.CLASS,UIManager.getInstance().businessEntityInfos(object.getClass()).getIdentifier()
							,UniformResourceLocatorParameter.CRUD,UniformResourceLocatorParameterBusinessImpl.getCrudAsString(crud)
							,UniformResourceLocatorParameter.IDENTIFIABLE, Crud.CREATE.equals(crud) ? null : object
									,UIManager.getInstance().businessEntityInfos(nodeObject.getClass()).getIdentifier()
									,nodeObject instanceof AbstractIdentifiable ? ((AbstractIdentifiable)nodeObject).getIdentifier() : null};
				}
				return parameters;
			}
						
			@Override
			public Boolean isRedirectable(TreeNode node) {
				return Boolean.TRUE; //nodeModel(node).getData() instanceof AbstractIdentifiable;
			}
			
			@Override
			public AbstractTree<TreeNode, HierarchyNode> getTree() {
				return Tree.this;
			}
		});
		
		redirectable = Boolean.TRUE;
	}
	
	@Override
	protected void __redirectTo__(TreeNode node, String viewId,Object[] parameters) {
		WebNavigationManager.getInstance().redirectTo(viewId,parameters);
	}
	
	@Override
	public <TYPE> void build(Class<TYPE> aClass, Collection<TYPE> aCollection,TYPE selected) {
		super.build(aClass, aCollection, selected);
		model = new DefaultDiagramModel();
		model.setMaxConnections(-1);
        
        /*Element elementA = new Element("A", "20em", "6em");
        elementA.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
         
        Element elementB = new Element("B", "10em", "18em");
        elementB.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));
         
        Element elementC = new Element("C", "40em", "18em");
        elementC.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));
        
        model.addElement(elementA);
        model.addElement(elementB);
        model.addElement(elementC);
         
        model.connect(new Connection(elementA.getEndPoints().get(0), elementB.getEndPoints().get(0)));        
        model.connect(new Connection(elementA.getEndPoints().get(0), elementC.getEndPoints().get(0)));
        */
		
		Element ceo = new Element("CEO", "25em", "6em");
        ceo.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
        model.addElement(ceo);
         
        //CFO
        Element cfo = new Element("CFO", "10em", "18em");
        cfo.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        cfo.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
         
        Element fin = new Element("FIN", "5em", "30em");
        fin.addEndPoint(createEndPoint(EndPointAnchor.TOP));
         
        Element pur = new Element("PUR", "20em", "30em");
        pur.addEndPoint(createEndPoint(EndPointAnchor.TOP));
         
        model.addElement(cfo);
        model.addElement(fin);
        model.addElement(pur);
         
        //CTO
        Element cto = new Element("CTO", "40em", "18em");
        cto.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        cto.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
         
        Element dev = new Element("DEV", "35em", "30em");
        dev.addEndPoint(createEndPoint(EndPointAnchor.TOP));
         
        Element tst = new Element("TST", "50em", "30em");
        tst.addEndPoint(createEndPoint(EndPointAnchor.TOP));
         
        model.addElement(cto);
        model.addElement(dev);
        model.addElement(tst);
         
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:3}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
                         
        //connections
        model.connect(new Connection(ceo.getEndPoints().get(0), cfo.getEndPoints().get(0), connector));        
        model.connect(new Connection(ceo.getEndPoints().get(0), cto.getEndPoints().get(0), connector));
        model.connect(new Connection(cfo.getEndPoints().get(1), fin.getEndPoints().get(0), connector));
        model.connect(new Connection(cfo.getEndPoints().get(1), pur.getEndPoints().get(0), connector));
        model.connect(new Connection(cto.getEndPoints().get(1), dev.getEndPoints().get(0), connector));
        model.connect(new Connection(cto.getEndPoints().get(1), tst.getEndPoints().get(0), connector));
	}
	
	private EndPoint createEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setStyle("{fillStyle:'#404a4e'}");
        endPoint.setHoverStyle("{fillStyle:'#20282b'}");
         
        return endPoint;
    }
	
	public <TYPE> void build(Class<TYPE> aClass,Collection<TYPE> aCollection,TYPE selected,String consultViewId){
		build(aClass, aCollection,selected);
		this.consultViewId = consultViewId;
		
	}
	
	/* Ajax behaviors */
	
	public void onNodeSelect(NodeSelectEvent event){
		nodeSelected(event.getTreeNode());
	}
	
	public void onNodeExpand(NodeExpandEvent event){
		nodeExpanded(event.getTreeNode());
	}
	
	public void onNodeCollapse(NodeCollapseEvent event){
		nodeCollapsed(event.getTreeNode());
	}
}
