package org.cyk.ui.api.data.collector.form;


/*public abstract class AbstractDynamicOneInstanceForm<DATA> extends AbstractOneInstanceForm<DATA> implements Serializable {

	private static final long serialVersionUID = 930035128436508708L;

	public AbstractDynamicOneInstanceForm(DATA data) {
		super(data);
		InputSetsPositions positions = null;
		if(Boolean.TRUE.equals(getCustomInputSetPositioning())){
			positions = data.getClass().getAnnotation(InputSetsPositions.class);
			layoutColumnCount = -1;
			for(InputSetPosition position : positions.value())
				if(position.column().index()>layoutColumnCount)
					layoutColumnCount = position.column().index();
			layoutColumnCount++;
		}
		
		Collection<Field> fields = CommonUtils.getInstance().getAllFields(data.getClass(), Input.class);
		
		if(data.getClass().isAnnotationPresent(InputSets.class)){
			InputSets inputSets = data.getClass().getAnnotation(InputSets.class);
			for(InputSet inputSet : inputSets.value()){
				DynamicInputSet<DATA> set = (DynamicInputSet<DATA>) createInputSet(text(inputSet.labelId()));
				if(positions!=null)
					for(InputSetPosition position : positions.value())
						if(position.identifier().equals(inputSet.identifier())){
							set.setPositionAnnotation(position);
							break;
						}
				set.setAnnotation(inputSet); 
				set.build(fields);
			}
		}else{
			DynamicInputSet<DATA> set = (DynamicInputSet<DATA>) createInputSet(null); 
			set.build(fields);
			set.setRenderInFieldSet(Boolean.FALSE);
		}
		
		Collections.sort(inputSets, new InputSetPositionComparator());
		
	}
	
	@Override
	protected AbstractInputSet<DATA> __createInputSet__() {
		return new DynamicInputSet<DATA>();
	}
	
	private class InputSetPositionComparator implements Comparator<AbstractInputSet<?>>, Serializable{

		private static final long serialVersionUID = -4578231758461069785L;

		@Override
		public int compare(AbstractInputSet<?> is1, AbstractInputSet<?> is2) {
			Integer i1 = index(is1.getPositionAnnotation()),i2 = index(is2.getPositionAnnotation());
			return i1.compareTo(i2);
		}
		
		private Integer index(InputSetPosition position){
			return position.column().index()+layoutColumnCount*position.row().index();
		}
		
	}

}*/
