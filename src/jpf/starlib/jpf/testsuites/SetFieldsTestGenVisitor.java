package starlib.jpf.testsuites;

import starlib.data.DataNode;
import starlib.data.DataNodeMap;
import starlib.formula.Variable;
import starlib.formula.heap.PointToTerm;

public class SetFieldsTestGenVisitor extends TestGenVisitor {

	public SetFieldsTestGenVisitor(TestGenVisitor that) {
		super(that);
	}
		
	@Override
	public void visit(PointToTerm term) {
		Variable[] vars = term.getVars();
		if(vars[0].getName().startsWith("newNode_") || finalVars.contains(vars[0].getName())) {
			return;
		}
		
		int length = vars.length;
		
		DataNode dn = DataNodeMap.find(term.getType());
		
		Variable[] fields = dn.getFields();

		String name0 = standardizeName(vars[0]);
		for (int i = 1; i < length; i++) {
			String nameI = vars[i].getName();
			if (nameI.startsWith("Anon_")) continue;
			nameI = standardizeName(vars[i]);
			test.append("\t\t" + name0 + "." + fields[i - 1].getName() + " = " + nameI + ";\n");
		}
	}
}
